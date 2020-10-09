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
		try
		{
			Ini setttings = new Ini(new File(SETTINGS_FILE_PATH));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void load(Ini setttings, SettingsSection section)
	{
		Map<Software, String> list;
		String sectionName;
		
		switch(section)
		{
			case LocalRepositories:
			{
				list = localRepositories;
				sectionName = "Local Repositories";
				break;
			}
			case NetworkRepositories:
			{
				list = networkRepositories;
				sectionName = "NetworkRepositories";
				break;
			}
			case OnlineRepositories:
			{
				list = onlineRepositories;
				sectionName = "OnlineRepositories";
				break;
			}
			case Arguments:
			{
				list = arguments;
				sectionName = "arguments";
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + section);
		}
		
		for (Map.Entry<Software, String> application : list.entrySet())
		{
			application.setValue(setttings.get(sectionName, application.getKey().name()));
		}
	}
	
	public String getLocalRepository(Software software)
	{
		return localRepositories.get(software);
	}
	
	public String getNetworkRepository(Software software)
	{
		return localRepositories.get(software);
	}
	
	public String getOnlineRepository(Software software)
	{
		return localRepositories.get(software);
	}
	
	public String getArguments(Software software)
	{
		return localRepositories.get(software);
	}
}