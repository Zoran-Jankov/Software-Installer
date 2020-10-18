package com.zoran.jankov.software_installer_test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.zoran_jankov.software_installer.app.InstallerSettings;

public class InstallerSettingsTest
{
	private static final String PATH = "/Installer-Settings-Test.ini";
	private InstallerSettings installerSettings = InstallerSettings.getInstance();
	
	public InstallerSettingsTest()
	{
		InstallerSettings.getInstance().loadSettings(PATH);
	}
	
	@Test
	public void getArgumentsTest()
	{
		assertEquals(installerSettings.getArguments("App1"), "/argument1");
		assertEquals(installerSettings.getArguments("App2"), "/argument2");
		assertEquals(installerSettings.getArguments("App3"), "/argument3");
		assertEquals(installerSettings.getArguments("App4"), null);
	}

	@Test
	public void getLocalPathTest()
	{
		assertEquals(installerSettings.getLocalPath("App1"), "D:/Program Installers/App1.exe");
		assertEquals(installerSettings.getLocalPath("App2"), "D:/Program Installers/App2.exe");
		assertEquals(installerSettings.getLocalPath("App3"), "D:/Program Installers/App3.exe");
		assertEquals(installerSettings.getLocalPath("App4"), null);
	}

	@Test
	public void getNetworkPathTest()
	{
		assertEquals(installerSettings.getNetworkPath("App1"), "//server/Program Installers/App1.exe");
		assertEquals(installerSettings.getNetworkPath("App2"), "//server/Program Installers/App2.exe");
		assertEquals(installerSettings.getNetworkPath("App3"), "//server/Program Installers/App3.exe");
		assertEquals(installerSettings.getNetworkPath("App4"), null);
	}

	@Test
	public void getDownloadURLTest()
	{
		assertEquals(installerSettings.getDownloadURL("App1"), "https://website/download-app1");
		assertEquals(installerSettings.getDownloadURL("App2"), "https://website/download-app2");
		assertEquals(installerSettings.getDownloadURL("App3"), "https://website/download-app3");
		assertEquals(installerSettings.getDownloadURL("App4"), null);
	}
}