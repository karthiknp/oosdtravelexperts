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
	<jsp:useBean id="login" class="LoginBean">

	</jsp:useBean>
    <div id="PageWrapper">

        <div id="MenuWrapper">
			<img src="pics/TXlogo.png" width="800" height="250" />
			<jsp:include page="parts/menu.html" />
        </div>            

        <div id="HeaderWrapper">
        &nbsp;
        </div>
                    
        <div id="ContentWrapper"><div style="padding-left: 50px;">
                                
            <div id="SidebarWrapper">
            &nbsp;
            </div>
            
            <h4>Thursday, September 10, 2009</h4>
            <p>Web launch added, accessable for all authenticated agents through the "Agent" page</p>
            
            <div id="FormWrapper">
            	&nbsp;
            </div>
        </div></div>

        <div id="FooterWrapper">
            <jsp:include page="parts/footer.html" />
            <p>The server time is: <today:txtags/></p>
        </div>
    </div>
</body>
</html>