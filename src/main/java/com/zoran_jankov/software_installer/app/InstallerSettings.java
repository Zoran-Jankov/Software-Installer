package com.zoran_jankov.software_installer.app;

import java.io.IOException;
import java.io.InputStream;

import org.ini4j.Ini;

public class InstallerSettings
{
	private static InstallerSettings instance;
	
	private Ini settings;

    public static synchronized InstallerSettings getInstance()
    {
        if(instance == null)
        {
            instance = new InstallerSettings();
        }
        return instance;
    }

	private InstallerSettings()
	{
		
	}
	
	public void loadSettings(String path)
	{
		InputStream inputStream = getClass().getResourceAsStream(path);

		try
		{
			settings = new Ini(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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

	public String getIconPath(String application)
	{
		return settings.get(application, Option.ICON);
	}
}