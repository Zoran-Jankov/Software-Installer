package com.zoran_jankov.software_installer.app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class InstallerManager
{
	private InstallerSettings settings;

	public InstallerManager(InstallerSettings settings)
	{
		this.settings = settings;
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

	public void downloadFromInternet(String application)
	{
		File destination = new File(settings.getLocalPath(application));
		URL source = null;

		try
		{
			source = new URL(settings.getDownloadURL(application));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		try
		{
			FileUtils.copyURLToFile(source, destination);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void downloadFromNetwork(String application)
	{
		File source = new File(settings.getNetworkPath(application));
		File destination = new File(settings.getLocalPath(application));

		try
		{
			FileUtils.copyFile(source, destination);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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