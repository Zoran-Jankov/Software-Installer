package com.zoran_jankov.software_installer.app;

import java.io.IOException;
import java.io.InputStream;

import org.ini4j.Ini;

public class SettingsManager
{
	public Ini settings = loadInstallerSettings();
	
	public SettingsManager()
	{
		
	}
	
	public Ini loadInstallerSettings()
	{
		Ini settings = new Ini();
		try
		{
			ClassLoader classLoader = getClass().getClassLoader();
	        InputStream inputStream = classLoader.getResourceAsStream("Installer-Settings.ini");
	        settings.load(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return settings;
	}
}