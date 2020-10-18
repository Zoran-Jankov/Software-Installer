package com.zoran_jankov.software_installer.app;

import com.zoran_jankov.software_installer.controller.MainWindowController;

public class SoftwareInstaller
{
	private static final String INSTALLER_SETTINGS_PATH = "/Installers-Settings.ini";
	
	public static void main(String[] args)
	{	
		InstallerSettings.getInstance().loadSettings(INSTALLER_SETTINGS_PATH);
		new MainWindowController();
	}
}