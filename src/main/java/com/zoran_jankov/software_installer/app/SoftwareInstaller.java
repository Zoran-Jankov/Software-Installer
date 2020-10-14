package com.zoran_jankov.software_installer.app;

public class SoftwareInstaller
{
	public static void main(String[] args)
	{	
		InstallerSettings settings = new InstallerSettings("Installers-Settings.ini");
		String test = settings.getArguments("AIMP");
		System.out.println(test);
	}
}