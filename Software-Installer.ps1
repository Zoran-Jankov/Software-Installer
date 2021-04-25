#---------------------------------------------------------[Initialisations]--------------------------------------------------------
$SoftwareList = Import-Csv -Path '.\SoftwareList.csv' -Delimiter ';' -Encoding 'UTF8'
[hashtable]$AppSelection = @{}
[hashtable]$AppSize = @{}
[hashtable]$AppPresent = @{}
$SizeSum = 0

$ApplicationTitle = "Software Installer"
$ButtonBackgroundColor = "#003F9A"
$FormBackgroundColor = "#468FEA"
$FormForegroundColor = "#FFFFFF"

$MarginSize = 25
$NumberOfColumns = 3
$CheckboxWidth = 200
$ComboBoxHeight = 25
$ColumnWidth = 200
$TextBoxHeight = 150
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
$MainFormWidth = $ComboBoxBlockWidth + ($MarginSize * 2)
$MainFormHeight = $ComboBoxBlockHeight + $TextBoxHeight + $ProgressBarHeight + $ButtonHeight + ($MarginSize * 4)
$TextBoxPosition = $ComboBoxBlockHeight + $MarginSize
$ProgressBarPosition = $TextBoxPosition+ $MarginSize + $TextBoxHeight
$ButtonPosition = $ProgressBarPosition + $MarginSize + $ProgressBarHeight

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

$XAxisCheckbox = $MarginSize
$YAxisCheckbox = $MarginSize
$ColumnCounter = 1

foreach ($Application in $SoftwareList) {
    $SoftwareCheckbox = New-Object System.Windows.Forms.CheckBox
    $SoftwareCheckbox.AutoSize = $false
    $SoftwareCheckbox.Size = New-Object System.Drawing.Size($CheckboxWidth, $ComboBoxHeight)
    $SoftwareCheckbox.Location = New-Object System.Drawing.Size($XAxisCheckbox, $YAxisCheckbox)
    $SoftwareCheckbox.Text = $Application.Name
    $SoftwareCheckbox.Checked = $false
    $SoftwareCheckbox.Font = New-Object System.Drawing.Font('Microsoft Sans Serif', 10, [System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
    $MainForm.controls.Add($SoftwareCheckbox)

    $Size = (Get-Item -Path $Application.Path).Length
    $AppSize.Add($Application.Name, $Size)

    if ($ColumnCounter -lt $NumberOfColumns) {
        $XAxisCheckbox += $ColumnWidth
        $ColumnCounter += 1
    }
    else {
        $XAxisCheckbox = $MarginSize
        $YAxisCheckbox += $ComboBoxHeight
        $ColumnCounter = 1
    }
    $AppSelection.Add($Application.Name, $SoftwareCheckbox)
}

$TextBox = New-Object System.Windows.Forms.TextBox
$TextBox.Location = New-Object System.Drawing.Point($MarginSize, $TextBoxPosition)
$TextBox.Size = New-Object System.Drawing.Size($ComboBoxBlockWidth, $TextBoxHeight)
$TextBox.AutoSize = $false
$TextBox.Multiline = $true
$TextBox.Scrollbars = "Vertical"
$TextBox.ReadOnly = $true
$MainForm.Controls.Add($TextBox)

$ProgressBar = New-Object System.Windows.Forms.ProgressBar
$ProgressBar.Location = New-Object System.Drawing.Point($MarginSize, $ProgressBarPosition)
$ProgressBar.Size = New-Object System.Drawing.Size($ComboBoxBlockWidth, $ProgressBarHeight)
$ProgressBar.Style = "Continuous"
$ProgressBar.Minimum = 0
$ProgressBar.Maximum = 100
$MainForm.Controls.Add($ProgressBar)

$InstallButton = New-Object system.Windows.Forms.Button
$InstallButton.Text = "Install"
$InstallButton.Width = $ButtonWidth
$InstallButton.Height = $ButtonHeight
$InstallButton.Location = New-Object System.Drawing.Point((($MainFormWidth - $ButtonWidth) / 2), $ButtonPosition)
$InstallButton.Font = New-Object System.Drawing.Font('Microsoft Sans Serif', 10, [System.Drawing.FontStyle]([System.Drawing.FontStyle]::Bold))
$InstallButton.BackColor = $ButtonBackgroundColor
$MainForm.controls.Add($InstallButton)

foreach ($Application in $SoftwareList) {
    if ($AppSelection[$Application.Name].Checked -eq $true) {
        $SizeSum += $AppSize[$Application.Name]
    }
}

foreach ($Application in $SoftwareList) {
    if ($AppSelection[$Application.Name].Checked -eq $true) {
        $AppPresent[$Application.Name] = ($AppSize[$Application.Name] / $SizeSum) * 100
    }
}

$InstallButton.Add_Click({
        foreach ($Application in $SoftwareList) {
            $Progress = 0
            if ($AppSelection[$Application.Name].Checked -eq $true) {
                $TextBox.Text += ("Installing " + $Application.Name + " . . .`r`n")
                Start-Process -FilePath $Application.Path -ArgumentList $Application.Arguments | Get-Process | Wait-Process
                $Progress += $AppPresent[$Application.Name]
                $ProgressBar.Value = $Progress
                $TextBox.Text += ($Application.Name + " application installed`r`n")
            }
        }
    })  

[void]$MainForm.ShowDialog()