<%@ 
	page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
	import="java.sql.*,java.util.*"
	errorPage="error.jsp" 
%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="today" %>
<%!
	// Design/Graphic/CSS/JSP/Applet by Will Dixon
	// Original ASP code by Mark Southwell from Team Project #2
	
	ResultSet rs;
	Connection dbConnection;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="parts/head.html" />

<body>
    <div id="PageWrapper">

        <div id="MenuWrapper">
			<img src="pics/TXlogo.png" width="800" height="250" />
			<jsp:include page="parts/menu.html" />
        </div>            

        <div id="HeaderWrapper">
        &nbsp;
        </div>
                    
        <div id="ContentWrapper">
                                
            <div id="SidebarWrapper">
            &nbsp;
            </div>
            
<!--  Simple login system using sessions by Will Dixon -->
<%
// START JSP

if((session.getAttribute("loggedIn") == null)) {
	// Display login form only if not logged in or form submitted
	%>
			<form method="post" action="index.jsp">
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
				<td><input type="submit" value="Log In" /></td>
			</tr>	
			</table>
			</form>
	<%
}	
if (request.getParameter("username") != null) {
	// Form posted, so try to authenticate
	// Save to session if auth succesful
	try {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ictoosd", "ictoosd");
		
		// Use prepared statement for safe parameters
		// NEVER build SQL statements with string functions
		PreparedStatement pstQuery = dbConnection.prepareStatement("SELECT * FROM Customers WHERE {fn LCASE(CustLastName)} = ? AND Password = ?");
		pstQuery.setString(1, request.getParameter("username").toLowerCase());
		pstQuery.setString(2, request.getParameter("password"));
		rs = pstQuery.executeQuery();
				
		if(rs.next()) {	// Passed authentication
			// Save to session
			session.setAttribute("loggedIn", "true");
			session.setAttribute("sessUsername", rs.getString("CustLastName"));
			session.setAttribute("sessPassword", rs.getString("Password"));
		}
		else {			// Failed authentication
		%>
			<p>Sorry, you entered either an invalid username and/or password.</p>
		<% 
		}
	}
	catch(SQLException ex) {
		ex.printStackTrace();
	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
}

if(session.getAttribute("sessUsername") != null) {
	%>
	<p>Logged in as <%= session.getAttribute("sessUsername") %></p>
	<applet 
		archive="client.jar" 
		code="com.travelexperts.www.ClientSupportApplet"
		height="300"
		width="600"
		name="ClientSupport" 
		>
		<param name="username" value="<%= session.getAttribute("sessUsername") %>">
		<param name="password" value="<%= session.getAttribute("sessPassword") %>">
	</applet>
	<br/><br/>
	<button onclick="reconnect();">Reconnect to server</button> 
	<%
}
	%>	
			<a href="txapp.jnlp">Employees: here to launch Travel Experts Management System</a>		
            <div id="FormWrapper">
            	&nbsp;
            </div>
        </div>

        <div id="FooterWrapper">
            <jsp:include page="parts/footer.html" />
            <p>The server time is: <today:txtags/></p>
        </div>
    </div>
</body>
</html>