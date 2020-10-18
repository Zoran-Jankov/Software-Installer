package com.zoran_jankov.software_installer.controller;

import com.zoran_jankov.software_installer.app.InstallerManager;
import com.zoran_jankov.software_installer.app.Software;

public class MainWindowController
{
    private InstallerManager installerManager = new InstallerManager();
    
    public MainWindowController()
    {
        System.out.println(Software.CCLEANER);
        installerManager.downloadFromInternet(Software.CCLEANER);
    }
}