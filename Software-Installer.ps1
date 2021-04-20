#---------------------------------------------------------[Initialisations]--------------------------------------------------------
$SoftwareList = Import-Csv -Path '.\SoftwareList.csv' -Delimiter ';' -Encoding 'UTF8'
$AppSelection = @{}

$ApplicationTitle = "Software Installer"
$LabelBackColor = "#003f9a"
$FormBackgroundColor = "#468FEA"
$FormForegroundColor = "#FFFFFF"

$NumberOfColumns = 3
$MarginSize = 25
$ColumnWidth = 150
$InstallButtonWidth = 120
$InstallButtonHeight = 40

$MainFormWidth = ($MarginSize * 2) + ($NumberOfColumns * 150)
$MainFormHeight = (($SoftwareList | Measure-Object).Count / $NumberOfColumns) * $MarginSize + ($MarginSize * 3) + $InstallButtonHeight
if (($SoftwareList | Measure-Object).Count % $NumberOfColumns -lt 0) {
    $MainFormHeight += $MarginSize
}

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
    $SoftwareCheckbox.AutoSize = $true
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
        $YAxisOffset += $MarginSize
        $ColumnCounter = 1
    }
    $AppSelection.Add($Application.Name, $SoftwareCheckbox)
}

$InstallButton = New-Object system.Windows.Forms.Button
$InstallButton.Text = "Install"
$InstallButton.Width = $InstallButtonWidth
$InstallButton.Height = $InstallButtonHeight
$InstallButton.Location = New-Object System.Drawing.Point((($MainFormWidth - $InstallButtonWidth) / 2), ($MainFormHeight - $InstallButtonHeight- $MarginSize))
$InstallButton.Font = New-Object System.Drawing.Font('Microsoft Sans Serif',10,[System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
$InstallButton.BackColor = $LabelBackColor

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