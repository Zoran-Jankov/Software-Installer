package com.zoran_jankov.software_installer.app;

public class SoftwareInstaller
{
	public static void main(String[] args)
	{	
		SettingsManager settings = new SettingsManager();
		System.out.println(settings.settings.get("CCleaner", "Local Repository"));
	}
}