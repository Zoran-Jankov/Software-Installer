package com.zoran_jankov.software_installer.gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SelectAppPanel
{
	private JPanel panel = new JPanel();
	
	public SelectAppPanel()
	{
		JFrame t = new JFrame();
		 t.add(panel);
		 t.setVisible(true);
		panel.setLayout(new MigLayout("", "[][][]", "[][]"));
		
		JCheckBox chckbxSelectApp = new JCheckBox("");
		panel.add(chckbxSelectApp, "cell 0 0");
		
		JLabel lblAppIcon = new JLabel(new ImageIcon("D:\\Pictures\\Art\\Art\\Profile 100x100.png"));
		panel.add(lblAppIcon, "cell 1 0");
		
		JLabel lblAppName = new JLabel("Application");
		panel.add(lblAppName, "cell 2 0");
		
	}
}