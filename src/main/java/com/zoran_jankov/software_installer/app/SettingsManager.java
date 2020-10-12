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
		
			ClassLoader classLoader = getClass().getClassLoader();
	        InputStream inputStream = classLoader.getResourceAsStream("Installer-Settings.ini");
	        try {
				settings.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		return settings;
	}
}