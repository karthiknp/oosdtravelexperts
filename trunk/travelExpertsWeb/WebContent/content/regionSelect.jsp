<%@page 
	import="java.sql.*"
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	autoFlush="true"
%>
<%
	// Get request data
	String country = request.getParameter("country");
	if(country == null) {
		out.write("Error: no country specified");
		return;
	}
	
	// Execute query and build <select> form from results
	try {
		// Try to insert the new table
		
		PreparedStatement pst = TXConnection.getConnection()
			.prepareStatement("SELECT Code, Region FROM countryregions WHERE Country = ?");
		pst.setString(1, country);
		ResultSet rsRegions = pst.executeQuery();

		out.print("<select name='custProv' class='regionSelect'>");
		
		// Output either "State" or "Province"
		if(country.equals("USA"))
			out.print("<option>Select State</option>");
		else if(country.equals("Canada")) 
			out.print("<option>Select Province</option>");
		else
			out.print("<option>Country " + country + " not found!</option");
		
		while(rsRegions.next()) {
			%>
	
					
<%@page import="txweb.TXConnection"%><option value="<%= rsRegions.getString("Code") %>">
						<%= rsRegions.getString("Region") %>
					</option>
			<%
		}
		out.print("</select>");
	}
	catch(SQLException ex) {
		ex.printStackTrace();
	}

%>