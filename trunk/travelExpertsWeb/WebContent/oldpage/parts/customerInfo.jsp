<%@ 
	page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
	import="java.sql.*,java.util.*,connection.*"
%>
<%!Connection dbConnection;
	ResultSet rs;%>

<%
	// Output a table in html
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ictoosd", "ictoosd");
		PreparedStatement pst = dbConnection.prepareStatement("SELECT * FROM Customers WHERE {fn LCASE(CustLastName)} LIKE ?");
		pst.setString(1, request.getParameter("like").toLowerCase() + "%" );
		rs = pst.executeQuery();
		%>
		<table>
	<%	
	while(rs.next()) {
		%>
		<tr>	
			<td>
				<%= rs.getString("CustFirstName") %>				
			</td>			
			<td>
				<%= rs.getString("CustLastName") %>				
			</td>
			<td>
			</td>
			<td>
				<%= rs.getString("CustEmail") %>
			</td>
		</tr>			
		<%
	}
	%>
		</table>
	<%
	}
	catch(SQLException ex) { ex.printStackTrace(); %> <%= "Error" %>  <% }
%>
