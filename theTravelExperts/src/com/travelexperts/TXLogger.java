package com.travelexperts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

	private static String logFilePath = "errorlog.xml";
	//private static BufferedWriter logWriter;
	private static XMLStreamWriter xmlWriter;
	
	// Example of static constructor
 	static {
				
		try {
			xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileOutputStream(logFilePath));
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement("TXLogFile");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
 	}
 	
 	public static void logEvent(int eventLevel, String eventMessage) {
 		
 	}
 	
 	public static void logError(String errorMessage) {
 		logEvent(EVENT_ERROR, errorMessage);
 	}
}
