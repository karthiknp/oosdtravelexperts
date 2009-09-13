package com.travelexperts;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * 
 * @author Will_Dixon
 *
 * 
 *	Logger that saves errors/warnings in XML
 */
public class TXLogger {
	
	// A few different error levels
	public static final int EVENT_ERROR = 1;
	public static final int EVENT_WARNING = 2;
	public static final int EVENT_NOTICE = 3;
	public static final int EVENT_INFO = 4;
	
	private static final String[] EVENT_NAMES = { "", "Error", "Warning", "Notice", "Info" };

	private static String LOG_FILENAME = "errorlog.xml";
	
	private static BufferedWriter bfwLogger;
	
	// Example of static constructor
 	static {
				
		try {			
			bfwLogger = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG_FILENAME, true)));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
 	}
 	
 	public static void logEvent(int eventLevel, String eventMessage) {
 		try {
 			if(eventLevel > EVENT_NAMES.length) bfwLogger.write("Internal logger error: invalid error code");
 			 	bfwLogger.write(EVENT_NAMES[eventLevel] );
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
