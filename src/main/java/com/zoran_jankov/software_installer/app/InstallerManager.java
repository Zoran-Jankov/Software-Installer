package com.zoran_jankov.software_installer.app;

import java.util.Map;

public class InstallerManager
{
	private static InstallerManager instance;
	private Map<Software, String> arguments;
	
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