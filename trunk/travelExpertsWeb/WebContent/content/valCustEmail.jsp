<%@ page
	contentType="text/html; charset=ISO-8859-1"
	import="java.sql.*, txweb.*"
 %>
<%
	if(request.getParameter("id") != null) {
		PreparedStatement pst = TXConnection.getConnection()
		.prepareStatement("SELECT CustEmail FROM Customers WHERE CustEmail = ?");
		
		pst.setString(1, request.getParameter("id"));
		ResultSet rs = pst.executeQuery();

		if(rs.next()) {
			out.print("This email address is already in use");
		}
		else {
			out.print("E-mail confirmed");
		}
	}
	else
		out.print("Error: No email to verify");
%>
