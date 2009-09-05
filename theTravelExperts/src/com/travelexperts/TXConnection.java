package com.travelexperts;

// Import the JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TXConnection 
{
	Connection conn;
	public TXConnection() {

	    try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    
	    try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ictoosd", "ictoosd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getInstance() {
		return conn;
	}
	
}
