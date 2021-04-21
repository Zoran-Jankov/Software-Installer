#---------------------------------------------------------[Initialisations]--------------------------------------------------------
$SoftwareList = Import-Csv -Path '.\SoftwareList.csv' -Delimiter ';' -Encoding 'UTF8'
[hashtable]$AppSelection = @{}
[hashtable]$AppSize = @{}

$ApplicationTitle = "Software Installer"
$ButtonBackgroundColor = "#003F9A"
$FormBackgroundColor = "#468FEA"
$FormForegroundColor = "#FFFFFF"

$MarginSize = 25
$NumberOfColumns = 4
$ComboBoxWidth = 150
$ComboBoxHeight = 25
$ColumnWidth = 150
$ProgressBarHeight = 25
$ButtonWidth = 120
$ButtonHeight = 35

if ($SoftwareList.Count % $NumberOfColumns -gt 0) {
    $NumberOfRows = ($SoftwareList.Count / $NumberOfColumns) + 1
}
else {
    $NumberOfRows = $SoftwareList.Count / $NumberOfColumns
}
$ComboBoxBlockWidth = $NumberOfColumns * $ColumnWidth
$ComboBoxBlockHeight = $NumberOfRows * $ComboBoxHeight
$MainFormWidth = ($MarginSize * 2) + $ComboBoxBlockWidth
$MainFormHeight = $ComboBoxBlockHeight + ($MarginSize * 4) + $ButtonHeight
$ProgressBarPosition = $ComboBoxBlockHeight + $MarginSize

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
    $SoftwareCheckbox.Font = New-Object System.Drawing.Font('Microsoft Sans Serif', 10, [System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
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
    $AppSize.Add($Application.Name, (Get-Item -Path $Application.Path).Length)
}

$ProgressBar = New-Object System.Windows.Forms.ProgressBar
$ProgressBar.Location = New-Object System.Drawing.Point(25, $ProgressBarPosition)
$ProgressBar.Size = New-Object System.Drawing.Size($ComboBoxBlockWidth, $ProgressBarHeight)
$ProgressBar.Style = "Continuous"
$ProgressBar.Minimum = 0
$ProgressBar.Maximum = 10000
$MainForm.Controls.Add($ProgressBar)

$InstallButton = New-Object system.Windows.Forms.Button
$InstallButton.Text = "Install"
$InstallButton.Width = $ButtonWidth
$InstallButton.Height = $ButtonHeight
$InstallButton.Location = New-Object System.Drawing.Point((($MainFormWidth - $ButtonWidth) / 2), ($MainFormHeight - $ButtonHeight - $MarginSize))
$InstallButton.Font = New-Object System.Drawing.Font('Microsoft Sans Serif', 10, [System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
$InstallButton.BackColor = $ButtonBackgroundColor
$MainForm.controls.Add($InstallButton)

$InstallButton.Add_Click( {
        foreach ($Application in $SoftwareList) {
            if ($AppSelection[$Application.Name].Checked -eq $true) {
                Start-Process -FilePath $Application.Path -ArgumentList $Application.Arguments
                $ProgressBar.Value
            }
        }
    })  

[void]$MainForm.ShowDialog()