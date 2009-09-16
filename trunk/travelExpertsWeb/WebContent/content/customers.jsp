<%@ page
	autoFlush="true"
	contentType="text/html; charset=ISO-8859-1"
	import="java.sql.*,txweb.*"
 %>
<%
	if(request.getParameter("username") != null && request.getParameter("password") != null) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		out.write("<br/>");
		
		PreparedStatement pst = TXConnection.getConnection().prepareStatement
			("SELECT * FROM Customers WHERE CustEmail = ? AND Password = ?");
		pst.setString(1, username);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			out.write("Logged in as " + username);
		}
		else {
			out.write("Sorry!  Invalid email and/or password.");
		}
	}
	else {
%>
<form id='custLogin' >
<fieldset>
<legend>Customer Log-In</legend>
<table>
<tr>
	<td>Username: </td>
	<td><input type="text" name="username" /></td>
</tr>
<tr>
	<td>Password: </td>
	<td><input type="password" name="password" /></td>
</tr>
<tr>
	<td><button onclick="doPost('customers.jsp', 'custLogin');" 
		name="btnLogin" value="true">Log in</button></td>
</tr>	
</table>
</fieldset>
</form>

<%} // END ELSE %>
