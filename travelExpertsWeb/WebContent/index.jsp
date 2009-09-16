<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- NEW SITE BY WILL DIXON -->
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	
	<meta name="keywords" content="travel, vacation, flights, cruises, discount, packages" />
	<meta name="description" content="Travel Experts - Book Your Vacations Online Today!" />
	
	<title>The Travel Experts</title>
	<link rel="stylesheet" href="styles.css" />
	<script type="text/javascript" src="scripts.js">
	</script>
</head>
<body onload="">
<div id="wrapper">
	<div id="header">
		<img src="pics/txlogo.png" />
	</div>
	 
	 	<!-- 
	 <div id="header">
	 	<div id="login">
			<form id='custLogin' >
			<fieldset>
			<legend class="loginLabel">Customer Log-In</legend>
			<table>
			<tr>
				<td>Email:</td>
				<td><input class="loginInput" type="text" name="username" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input class="loginInput" type="password" name="password" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><button onclick="doPost('customers.jsp', 'custLogin');" 
					style="font: bold small 'trebuchet ms',helvetica,sans-serif; color:#050; "
					name="btnLogin" value="true">Log in</button></td>
			</tr>	
			</table>
			</fieldset>
			</form>
	 	</div>
	 </div>
	 	 -->
	 

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
