package com.travelexperts;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.stream.FactoryConfigurationError;

/**
 * 
 * @author Will_Dixon
 *
 *	Simple logger that appends timestamped message with error level to file 
 */
public class TXLogger {
	
	// A few different error levels
	public static final int EVENT_ERROR = 1;
	public static final int EVENT_WARNING = 2;
	public static final int EVENT_NOTICE = 3;
	public static final int EVENT_INFO = 4;
	
	private static final String[] EVENT_NAMES = { "Unknown", "Error", "Warning", "Notice", "Info" };

	private static String LOG_FILENAME = "txlog.txt";
	
	private static BufferedWriter bfwLogger;
	
	// Example of static constructor
 	static {
				
		try {			
			bfwLogger = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG_FILENAME, true)));
			logEvent(EVENT_INFO, "Log started");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
 	}
 	
 	private static void logEvent(int eventLevel, String eventMessage) {
 		try {
 			if(eventLevel >= EVENT_NAMES.length) {
 				bfwLogger.write("Internal logger error: invalid error code");
 				return;
 			}
 			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
 			bfwLogger.write(EVENT_NAMES[eventLevel] + " @ " + timeStamp + ": " + eventMessage);
 			bfwLogger.write("\r\n");
	 		bfwLogger.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}

 	
 	public static void logError(String errorMessage) {
 		logEvent(EVENT_ERROR, errorMessage);
 	}
 	public static void logWarning(String errorMessage) {
 		logEvent(EVENT_WARNING, errorMessage);
 	}
 	public static void logNotice(String errorMessage) {
 		logEvent(EVENT_NOTICE, errorMessage);
 	}
 	public static void logInfo(String errorMessage) {
 		logEvent(EVENT_INFO, errorMessage);
 	}
 	

}
