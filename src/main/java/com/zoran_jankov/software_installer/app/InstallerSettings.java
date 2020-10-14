package com.zoran_jankov.software_installer.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.ini4j.Ini;

public class InstallerSettings
{
	private Ini settings;

	public InstallerSettings(String path)
	{
		loadInstallerSettings(path);
	}

	public Set<String> getSoftwareSet()
	{
		return settings.keySet();
	}

	public String getArguments(String application)
	{
		return settings.get(application, Option.ARGUMENTS);
	}

	public String getLocalPath(String application)
	{
		return settings.get(application, Option.LOCAL_REPOSITORY);
	}

	public String getNetworkPath(String application)
	{
		return settings.get(application, Option.NETWORK_REPOSITORY);
	}

	public String getDownloadURL(String application)
	{
		return settings.get(application, Option.DOWNLOAD_URL);
	}

	public Ini loadInstallerSettings(String path)
	{
		Ini settings = new Ini();
		
		ClassLoader classLoader = getClass().getClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream(path);
		try
		{
			settings.load(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return settings;
	}
}