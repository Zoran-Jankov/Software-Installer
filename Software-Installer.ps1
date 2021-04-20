#---------------------------------------------------------[Initialisations]--------------------------------------------------------
$SoftwareList = Import-Csv -Path '.\SoftwareList.csv' -Delimiter ';' -Encoding 'UTF8'
$AppSelection = @{}

$ApplicationTitle = "Software Installer"
$ButtonBackgroundColor = "#003F9A"
$FormBackgroundColor = "#468FEA"
$FormForegroundColor = "#FFFFFF"

$MarginSize = 25
$NumberOfColumns = 4
$ComboBoxWidth = 150
$ComboBoxHeight = 25
$ColumnWidth = 150
$ButtonWidth = 120
$ButtonHeight = 35

if ($SoftwareList.Count % $NumberOfColumns -gt 0) {
    $NumberOfRows = ($SoftwareList.Count / $NumberOfColumns) + 1
}
else {
    $NumberOfRows = $SoftwareList.Count / $NumberOfColumns
}

$MainFormWidth = ($MarginSize * 2) + ($NumberOfColumns * $ColumnWidth)
$MainFormHeight = ($NumberOfRows * $ComboBoxHeight) + ($MarginSize * 2) + $ButtonHeight

#-----------------------------------------------------------[Functions]------------------------------------------------------------



#-----------------------------------------------------------[Execution]------------------------------------------------------------

Add-Type -AssemblyName System.Windows.Forms
[System.Windows.Forms.Application]::EnableVisualStyles()

$MainForm = New-Object system.Windows.Forms.Form
$MainForm.ClientSize = New-Object System.Drawing.Point($MainFormWidth, $MainFormHeight)
$MainForm.Text = $ApplicationTitle
$MainForm.TopMost = $true
$MainForm.FormBorderStyle = 'Fixed3D'
$MainForm.MaximizeBox = $false
$MainForm.ShowIcon = $false
$MainForm.BackColor = $FormBackgroundColor
$MainForm.ForeColor = $FormForegroundColor

$XAxisOffset = $MarginSize
$YAxisOffset = $MarginSize
$ColumnCounter = 1

foreach ($Application in $SoftwareList) {
    $SoftwareCheckbox = New-Object System.Windows.Forms.CheckBox
    $SoftwareCheckbox.AutoSize = $false
    $SoftwareCheckbox.Size = New-Object System.Drawing.Size($ComboBoxWidth, $ComboBoxHeight)
    $SoftwareCheckbox.Location = New-Object System.Drawing.Size($XAxisOffset, $YAxisOffset)
    $SoftwareCheckbox.Text = $Application.Name
    $SoftwareCheckbox.Checked = $false
    $SoftwareCheckbox.Font = New-Object System.Drawing.Font('Microsoft Sans Serif',10,[System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
    $MainForm.controls.Add($SoftwareCheckbox)

    if ($ColumnCounter -lt $NumberOfColumns) {
        $XAxisOffset += $ColumnWidth
        $ColumnCounter += 1
    }
    else {
        $XAxisOffset = $MarginSize
        $YAxisOffset += $ComboBoxHeight
        $ColumnCounter = 1
    }
    $AppSelection.Add($Application.Name, $SoftwareCheckbox)
}

$InstallButton = New-Object system.Windows.Forms.Button
$InstallButton.Text = "Install"
$InstallButton.Width = $ButtonWidth
$InstallButton.Height = $ButtonHeight
$InstallButton.Location = New-Object System.Drawing.Point((($MainFormWidth - $ButtonWidth) / 2), ($MainFormHeight - $ButtonHeight- $MarginSize))
$InstallButton.Font = New-Object System.Drawing.Font('Microsoft Sans Serif',10,[System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
$InstallButton.BackColor = $ButtonBackgroundColor

$MainForm.controls.AddRange(@(
    $InstallButton
    ))

$InstallButton.Add_Click({
    foreach ($Application in $SoftwareList) {
        if ($AppSelection[$Application.Name].Checked -eq $true) {
            Start-Process -FilePath $Application.Path -ArgumentList $Application.Arguments
        }
    }
})  

[void]$MainForm.ShowDialog()