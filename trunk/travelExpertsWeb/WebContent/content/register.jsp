<%@ page
	import="java.sql.*, txweb.*"
 %>
<% if(request.getParameter("custEmail") == null) { %>
	<form id="registerForm" name="customer">
	<fieldset>
	<legend style="font-size: 13px;"><strong>Register today and pay NO GST on your first order!</strong></legend>
	<table>
		<tr>
			<td><p class="formLabel">Email</p></td>
			<td><input class="formInput" type="text" name="custEmail" onblur="valCustEmail();" /></td>
			<td colspan="3">
				<div class="formHint">
					<div id="errCustEmail">Your email will be used to log in</div>
				</div>
			</td>
		</tr>
		<tr>
			<td><p class="formLabel">Password</p></td>
			<td><input class="formInput" type="password" name="custPassword" /></td>
	
			<td><p class="formLabel">Confirm Password</p></td>
			<td><input class="formInput" type="password" name="confirmPassword" /></td>
		</tr>
		<tr>
			<td><p class="formLabel">First Name</p></td>
			<td><input class="formInput" type="text" name="custFirstName" /></td>
	
			<td><p class="formLabel">Last Name</p></td>
			<td><input class="formInput" type="text" name="custLastName" /></td>
		</tr>
		<tr>
			<td><p class="formLabel">Country</p></td>
			<td><select class="formInput" name="custCountry">
					<option value="">Select Country</option>
					<option onclick ="loadRegions('Canada');" value="Canada">Canada</option>
					<option onclick="loadRegions('USA');" value="USA">USA</option>
				</select>
			</td>
	
			<td><p class="formLabel">Region</p></td>
			<td>
				<div id="optRegions">
					Select country first.
				</div>
			</td>
		</tr>
		<tr>
			<td><p class="formLabel">Address</p></td>
			<td colspan="4"><input class="formInput" style="width: 435px;" type="text" name="custAddress" /></td>
		</tr>
		<tr>
			<td><p class="formLabel">City</p></td>
			<td><input class="formInput" type="text" name="custCity" /></td>
	
			<td><p class="formLabel">Postal</p></td>
			<td><input class="formInput" type="text" name="custPostal" /></td>
		</tr>
		<tr>
			<td><p class="formLabel">Home Phone</p></td>
			<td><input class="formInput" type="text" name="custHomePhone" /></td>
	
			<td><p class="formLabel">Bus Phone</p></td>
			<td><input class="formInput" type="text" name="custBusPhone" /></td>
		</tr>
		<tr>
			<td><input type="button" name="btnRegister" value="Register" onclick="doPost('register.jsp', 'registerForm');" />
			</td>
		</tr>
	</table>
	</fieldset>
	</form>
	

<%} else {
	try {
		PreparedStatement pst = TXConnection.getConnection().prepareStatement
		("INSERT INTO Customers VALUES ( (SELECT MAX(CustomerID)+1 FROM CUSTOMERS), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, ?)");
		pst.setString(1, request.getParameter("custFirstName"));
		pst.setString(2, request.getParameter("custLastName"));
		pst.setString(3, request.getParameter("custAddress"));
		pst.setString(4, request.getParameter("custCity"));
		pst.setString(5, request.getParameter("custProv"));
		pst.setString(6, request.getParameter("custPostal"));
		pst.setString(7, request.getParameter("custCountry"));
		pst.setString(8, request.getParameter("custHomePhone"));
		pst.setString(9, request.getParameter("custBusPhone"));
		pst.setString(10,request.getParameter("custEmail"));
		pst.setString(11,request.getParameter("custPassword"));
		if(pst.executeUpdate() == 0) out.print("Unknown error creating account<br/>");
		pst.close();

		%>
		<p>Thank you for registering!</p>
		<p>You may now log into the <a href="#" onclick="loadContent('customers.jsp');">Customers</a> area.</p>
		<%
	}
	catch(SQLException e) {
		out.print(e.getMessage());
		e.printStackTrace();
	}
}%>
