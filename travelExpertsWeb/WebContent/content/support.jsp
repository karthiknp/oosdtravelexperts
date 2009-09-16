<%@ page
	import="java.sql.*,txweb.*"
 %>
<%

 %>
<applet 
	archive="TXSupportApplet.jar" 
	code="com.travelexperts.www.ClientSupportApplet"
	width="650px"
	height="350px"
	name="supportApplet">
	<%
	if(session.getAttribute("username") != null && session.getAttribute("password") != null) {
	%>
	<param name="username" value="<%= session.getAttribute("username") %>">
	<param name="password" value="<%= session.getAttribute("password") %>">
	<%}%>
</applet>
<br/>
<a href="#reconnect" onclick="appletReconnect();">Click Here to Reconnect</a>
