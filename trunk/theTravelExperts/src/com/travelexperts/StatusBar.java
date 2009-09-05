package com.travelexperts;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatusBar extends JLabel {

	private static String desktopMarkup = 
		"<html>" +
		"<h1>Welcome to the TravelExperts Content Management System!</h1>" +
		"<p>Latest updates:</p> " +
		"<ul>" +
		"<li>Sunday, August 31 - Created prototype GUI using InternalFrames</li>" +
		"<li>Sunday, August 31 - More test stuff</li>" +
		"</ul>" +
		"</html>" +
		"     ";		

	public StatusBar() throws MalformedURLException {
		/*
		super(desktopMarkup, 
				new ImageIcon(new URL("http://t3.gstatic.com/images?q=tbn:UrfL1-jyN2ZH9M:http://krisyee.files.wordpress.com/2009/05/hawaii1.jpg"), "TravelXprtsIcon"),
				JLabel.CENTER);
		*/
		super(desktopMarkup);
		
    	this.setBorder(BorderFactory.createRaisedBevelBorder());
    	this.setOpaque(true);
    	this.setBackground(Color.CYAN);
    	this.setHorizontalAlignment(JLabel.HORIZONTAL);
    	//this.setPreferredSize(new java.awt.Dimension(696, 140));
    	validate();

	}


}
