package com.zoran_jankov.software_installer.app;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.ini4j.Ini;

public class InstallerManager
{
	private static InstallerManager instance;
	private Map<Software, String> arguments;
	
	private InstallerManager()
	{
		arguments = getArguments();
	}
	
	public static InstallerManager getInstance()
	{
		if(instance == null)
		{
			instance = new InstallerManager();
		}
	    return instance;
	}
	
	private Map<Software, String> getArguments()
	{
		try
		{
			Ini setttings = new Ini(new File("D:/Programing\\Java\\Software Installer/src/main/resources/settings.ini"));
			System.out.println(setttings.get("Arguments", "CCleaner"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return arguments;
	}
	
	private void install(Software software)
	{
		//TODO Install implementation
	}
	
	public void startInstallation(Map<Software, Boolean> softwareList)
	{
		for (Map.Entry<Software, Boolean> application : softwareList.entrySet())
		{
			if(application.getValue())
			{
				install(application.getKey());
			}
		}
	}
	
	public void update()
	{
		
	}
}