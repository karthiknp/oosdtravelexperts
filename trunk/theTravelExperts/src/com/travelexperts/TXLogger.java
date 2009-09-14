package com.travelexperts;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * 
 * @author Will_Dixon
 * 
 * 
 *         Logger that saves errors/warnings in XML
 */
public class TXLogger extends Logger
{
	protected static final String LOG_NAME = "TXLog";
	static Logger logger = Logger.getLogger(LOG_NAME);

	protected TXLogger(String name)
	{
		super(LOG_NAME);
	}

	public static Logger getLogger()
	{
		Layout layout1=new PatternLayout("%c -- %p -- %m%n");
		logger.setLevel(Level.DEBUG);
		ConsoleAppender appender1 = new ConsoleAppender(layout1);
		logger.addAppender(appender1);
		appender1.setName("Console Appender");
		Layout layout2=new PatternLayout("%c %d{ISO8601} -- %p -- %m%n");
		try
		{
			Appender  appender2= new FileAppender(layout2,"travelExperts.log");
			appender2.setName("File Appender");
			logger.addAppender(appender2);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logger;
	}
}
