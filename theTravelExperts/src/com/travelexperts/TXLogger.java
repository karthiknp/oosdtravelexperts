package com.travelexperts;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.stream.FactoryConfigurationError;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * 
 * @author Will Dixon & Ingrid Niu
 *
 * Combined both lol, needs to be cleaned up
 * 
 */
public class TXLogger extends Logger
{
	// Ingrids log4J stuff
	protected static final String LOG_NAME = "TXLog";
	static Logger logger = Logger.getLogger(LOG_NAME);
	static {
		Layout layout1 = new PatternLayout("%c -- %p -- %m%n");
		logger.setLevel(Level.DEBUG);
		ConsoleAppender appender1 = new ConsoleAppender(layout1);
		logger.addAppender(appender1);
		appender1.setName("Console Appender");
		Layout layout2 = new PatternLayout("%c %d{ISO8601} -- %p -- %m%n");
		try
		{
			Appender appender2 = new FileAppender(layout2, "travelExperts.log");
			appender2.setName("File Appender");
			logger.addAppender(appender2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	protected TXLogger(String name)
	{
		super(LOG_NAME);
	}

	// Wills stuff

	// A few different error levels
	public static final int EVENT_ERROR = 1;
	public static final int EVENT_WARNING = 2;
	public static final int EVENT_NOTICE = 3;
	public static final int EVENT_INFO = 4;

	public static final String[] EVENT_NAMES = { "Unknown", "Error",
			"Warning", "Notice", "Info" };

	protected static String LOG_FILENAME = "txlog.txt";

	private static BufferedWriter bfwLogger;

	// Example of static constructor
	static
	{

		try
		{
			bfwLogger = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(LOG_FILENAME, true)));
			logEvent(EVENT_INFO, "Log started");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (FactoryConfigurationError e)
		{
			e.printStackTrace();
		}
	}

	private static void logEvent(int eventLevel, String eventMessage)
	{
		try
		{
			if (eventLevel >= EVENT_NAMES.length)
			{
				bfwLogger.write("Internal logger error: invalid error code");
				return;
			}
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(Calendar.getInstance().getTime());
			bfwLogger.write(EVENT_NAMES[eventLevel] + " @ " + timeStamp + ": "
					+ eventMessage);
			bfwLogger.write("\r\n");
			bfwLogger.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void logError(String errorMessage)
	{
		logEvent(EVENT_ERROR, errorMessage);
	}

	public static void logWarning(String errorMessage)
	{
		logEvent(EVENT_WARNING, errorMessage);
	}

	public static void logNotice(String errorMessage)
	{
		logEvent(EVENT_NOTICE, errorMessage);
	}

	public static void logInfo(String errorMessage)
	{
		logEvent(EVENT_INFO, errorMessage);
	}

}
