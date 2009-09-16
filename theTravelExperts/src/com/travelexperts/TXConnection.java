package com.travelexperts;

// Import the JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author will_ad
 */
public class TXConnection 
{
	// TODO: load from properties file
	private static String dbDriverClassname = "oracle.jdbc.driver.OracleDriver";
	private static String dbConnectionString = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbUsername = "ictoosd";
	private static String dbPassword = "ictoosd";
	
	private static Connection dbConnection = null;
	
	static {
	    try {
			Class.forName(dbDriverClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    
	    try {
			dbConnection = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static Connection getConnection() {
		return dbConnection;
	}
		
	// Deprecated but kept for compatability for time being, use static methods instead
	private Connection conn;
	public TXConnection() {
	    try {
			Class.forName(dbDriverClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    
	    try {
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Deprecated
	public Connection getInstance() {
		return conn;
	}	
	
}
