<%@ 
	page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
	import="java.sql.*,java.util.*,com.travelexperts.www.*"
	errorPage="error.jsp" 
%>
<%! Connection dbConnection; %>

<%
	// Output a table in html
	dbConnection = TXConnection.getConnection();
	ResultSet rs = dbConnection.createStatement().executeQuery("SELECT * FROM Customers");
	
%>
