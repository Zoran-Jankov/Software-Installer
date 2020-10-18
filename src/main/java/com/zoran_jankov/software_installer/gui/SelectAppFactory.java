package com.zoran_jankov.software_installer.gui;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import com.zoran_jankov.software_installer.app.InstallerSettings;

public class SelectAppFactory
{
	public static InstallerSettings settings = InstallerSettings.getInstance();
	
	public static JCheckBox getInstance(String application)
	{
		JCheckBox selectApp = new JCheckBox(application);
		URL iconURL = SelectAppFactory.class.getClass().getResource(settings.getIconPath(application));
		ImageIcon icon = new ImageIcon(iconURL);
		selectApp.setIcon(icon);
		 
		return selectApp;
	}
}