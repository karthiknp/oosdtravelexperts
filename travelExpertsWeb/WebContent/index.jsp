<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>The Travel Experts</title>
	<link rel="stylesheet" href="styles.css" />
	<script type="text/javascript" src="scripts.js">
	</script>
</head>
<body onload="ajaxInit(); loadContent('home.jsp');">
<div id="wrapper">
	<div id="header">
		<img src="pics/txlogo.png" />
	</div>

	<%	// request processing
	
	
	%>
	<div id="menu">
		<ul>
			<li><a href="#home" onclick="loadContent('home.jsp')">Home</a></li>
			<li><a href="#packages" onclick="loadContent('packages.jsp')">Packages</a></li>
			<li><a href="#register" onclick="loadContent('register.jsp')">Register</a></li>
			<li><a href="#customers" onclick="loadContent('customers.jsp')">Customers</a></li>
			<li><a href="#agents" onclick="loadContent('agents.jsp')">Agents</a></li>
			<li><a href="#support" onclick="loadContent('support.jsp')">Live Support</a></li>
			<li><a href="#contact" onclick="loadContent('contact.jsp')" >Contact</a></li>
		</ul>
	</div>


	<div id="content">
	<noscript>Sorry, you must enable JavaScript to use this website!</noscript>
	</div>
	
	<div id="footer">
	&copy; 2009 The Travel Experts
	</div>
</div>
</body>
</html>
