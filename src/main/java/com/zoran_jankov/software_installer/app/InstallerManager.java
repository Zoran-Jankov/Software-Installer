package com.zoran_jankov.software_installer.app;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class InstallerManager
{
	private InstallerSettings settings;

	public InstallerManager()
	{
		settings = new InstallerSettings("D:\\Programing\\Java\\Software Installer\\src\\main\\resources\\Installer-Setting.ini");
	}

	private void install(String application)
	{
		String installerPath = settings.getLocalPath(application);
		String arguments = settings.getArguments(application);

		try
		{
			Process installer = new ProcessBuilder(installerPath, arguments).start();
			installer.waitFor();
		}
		catch (IOException | InterruptedException e)
		{
			 e.printStackTrace();
		}
	}

	public void download(String software)
	{
		
	}
	
	public void startInstallation(Map<String, Boolean> softwareToInstall)
	{
		for (Entry<String, Boolean> application : softwareToInstall.entrySet())
		{
			if(application.getValue())
			{
				install(application.getKey());
			}
		}
	}
}