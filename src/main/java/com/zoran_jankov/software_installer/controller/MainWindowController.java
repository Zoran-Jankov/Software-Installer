package com.zoran_jankov.software_installer.controller;

import com.zoran_jankov.software_installer.app.InstallerManager;
import com.zoran_jankov.software_installer.app.InstallerSettings;
import com.zoran_jankov.software_installer.app.Software;

public class MainWindowController
{
    private static final String INSTALLER_SETTINGS_PATH = "/Installers-Settings.ini";
    private InstallerSettings installerSettings = new InstallerSettings(INSTALLER_SETTINGS_PATH);
    private InstallerManager installerManager = new InstallerManager(installerSettings);
    
    public MainWindowController()
    {
        System.out.println(Software.CCLEANER);
        installerManager.downloadFromInternet(Software.CCLEANER);
    }
}