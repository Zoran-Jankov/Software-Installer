package com.zoran_jankov.software_installer.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ini4j.Ini;

public class Settings
{
	private static Settings instance;
	
	private static final String SETTINGS_FILE_PATH = "D:/Programing/Java/Software Installer/src/main/resources/Settings.ini";
	
	private Map<Software, String> localRepositories = new HashMap<Software, String>();
	private Map<Software, String> networkRepositories = new HashMap<Software, String>();
	private Map<Software, String> onlineRepositories = new HashMap<Software, String>();
	private Map<Software, String> arguments = new HashMap<Software, String>();
	
	private Settings()
	{
		loadSettings();
	}
	
	public static Settings getInstance()
	{
		if(instance == null)
		{
			instance = new Settings();
		}
	    return instance;
	}
	
	public void loadSettings()
	{
		Ini settings = null;
		
		try
		{
			settings = new Ini(new File(SETTINGS_FILE_PATH));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		localRepositories = getInstallerSettings(settings, "Local Repositories");
			
		networkRepositories = getInstallerSettings(settings, "Network Repositories");
				
		onlineRepositories = getInstallerSettings(settings, "Online Repositories");
				
		arguments = getInstallerSettings(settings, "Arguments");

	}
	
	private Map<Software, String> getInstallerSettings(Ini setttings, String sectionName)
	{
		Map<Software, String> list = new HashMap<Software, String>();
		
		for (Software installer : Software.values())
		{
			list.put(installer, setttings.get(sectionName, installer.name()));
		}
		
		return list;
	}
	
	public String getLocalRepository(Software software)
	{
		return localRepositories.get(software);
	}
	
	public String getNetworkRepository(Software software)
	{
		return networkRepositories.get(software);
	}
	
	public String getOnlineRepository(Software software)
	{
		return onlineRepositories.get(software);
	}
	
	public String getArguments(Software software)
	{
		return arguments.get(software);
	}
}