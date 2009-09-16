<%@ page
	language="java"
	pageEncoding="ISO-8859-1" 
	autoFlush="true"
	import="java.sql.*, txweb.*, java.text.NumberFormat"
%>
<%!
	ResultSet rsPackages;
%>
<%
	rsPackages = TXConnection.getConnection().createStatement().executeQuery("SELECT * FROM Packages");

	out.print("<table>");
	while(rsPackages.next()) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();

		out.write("<tr>");
		out.write("<td>" + rsPackages.getString("PkgName") + "</td>" );
		out.write("<td>" + nf.format(Integer.parseInt(rsPackages.getString("PkgBasePrice"))) + "</td>");
		
		out.write("</tr>");
	}
	out.print("<table>");
	
%>
