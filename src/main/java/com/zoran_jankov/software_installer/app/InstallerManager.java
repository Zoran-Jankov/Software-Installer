package com.zoran_jankov.software_installer.app;

import java.io.IOException;
import java.util.Map;

public class InstallerManager
{
	private static InstallerManager instance;
	
	private InstallerManager()
	{
		
	}
	
	public static InstallerManager getInstance()
	{
		if(instance == null)
		{
			instance = new InstallerManager();
		}
	    return instance;
	}
	
	public void install(Software software)
	{
		try
		{
			new ProcessBuilder(Settings.getInstance().getLocalRepository(software), 
							   Settings.getInstance().getArguments(software)).start();							 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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