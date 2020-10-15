package com.zoran_jankov.software_installer.controller;

import com.zoran_jankov.software_installer.app.InstallerManager;
import com.zoran_jankov.software_installer.app.InstallerSettings;

public class MainWindowController
{
    private static final String INSTALLER_SETTINGS_PATH = "/Installer-Settings.ini";
    private InstallerSettings installerSettings = new InstallerSettings(INSTALLER_SETTINGS_PATH);
    private InstallerManager installerManager = new InstallerManager(installerSettings);
}