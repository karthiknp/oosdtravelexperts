<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Travel Experts - Error Encountered</title>
<link rel="stylesheet" href="styles.css" />
</head>
<body>
<div id="PageWrapper">
    <!-- updated logo and menu by Will Dixon -->
     <div style="float:right; font-size:80%; padding: 0 30px;">
     </div>

     <div id="MenuWrapper">
<img src="pics/TXlogo.png" width="800" height="250" />

         <img src="pics/TXmenumap.png" usemap="#MenuMap" width="800" alt="Navigation" style="border: none;" />
         <map name="MenuMap" id="MenuMap">
           <area shape="rect" coords="49,38,163,84" href="#" alt="Home" />
           <area shape="rect" coords="194,38,367,84" href="#" alt="Packages" />
           <area shape="rect" coords="400,38,558,84" href="#" alt="Register" />
           <area shape="rect" coords="591,38,740,84" href="#" alt="Contact" />
         </map>            
     </div>            

     <div id="HeaderWrapper">
     &nbsp;
     </div>
                 
     <div id="ContentWrapper">
     
         <div id="SidebarWrapper" >
             <img src="pics/bluestrip2.jpg" />
         </div>
         
    <h3>We are sorry, this page encountered an error while processing</h3>
	<%= exception.getMessage() %>
	<% exception.printStackTrace(); %>
	
            
         
         <div id="FormWrapper">
         	&nbsp;
         </div>
     </div>

     <div id="FooterWrapper">
         &copy; 2009 - The Travel Experts
     </div>
 </div>	
</body>
</html>