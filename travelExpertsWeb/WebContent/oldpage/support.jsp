 <%	// JSP
 	if(session.getAttribute("loggedIn") == null) {
 		%>
 		<p>
  		You must log on before you can access the online support system.
 		</p>
 		<% 
 	}
 	else {
 		%>
 		<p>
 			Welcome, <%= session.getAttribute("sessUsername") %>. <br/>
       			Please be patient while the applet loads.
</p>
<br/>
<applet 
	archive="TXSupportApplet.jar" 
	code="com.travelexperts.www.ClientSupportApplet"
	width="650px"
	height="350px"
	name="SupportApplet">
	
	<param name="username" value="<%= session.getAttribute("sessUsername") %>">
<param name="password" value="<%= session.getAttribute("sessPassword") %>">
</applet>
<br/>
<button type="button" onClick="document.applets.SupportApplet.reconnect()">Reconnect to server</button>
 		<% 
 	}
 
 %>
