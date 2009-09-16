
/*
 * Simple AJAX and validation library written by Will Dixon
 * 
 * Too many hours spent debugging :( :(
 */


function ajaxInit() {
  var request = null;
  if (window.ActiveXObject) {
	  request = new ActiveXObject("Microsoft.XMLHTTP");
  } else if (window.XMLHttpRequest) {
	  request = new XMLHttpRequest();
  } else {
	  return(null);
  }
  return request;
}

// Load a page in the content div
function loadContent(url) {
	var request = ajaxInit();
	request.onreadystatechange = function() {
		if(request.readyState == 4) {
			document.getElementById("content").innerHTML = request.responseText;
		}
	};
	request.open("get", "content/" + url, true);
	request.send(null);
}

// Validate email in registration form
function valCustEmail() {
	
	var request = ajaxInit();
	request.onreadystatechange = function() {
		if(request.readyState == 4) {
			document.getElementById("errCustEmail").innerHTML =	request.responseText;
		}
	};
	request.open("get", "content/valCustEmail.jsp?id=" + document.customer.custEmail.value, true);
	request.send(null);
}

// Calls reconnect method within the support applet 
function appletReconnect() {
	var supportApplet = document.applets.supportApplet;
	supportApplet.reconnect();
}

// Display region menu options based on which country selected
function loadRegions(country) {
	var request = ajaxInit();
	request.onreadystatechange = function() {
		if(request.readyState == 4) {
			document.getElementById("optRegions").innerHTML = request.responseText;
		}
	};
	request.open("get", "content/regionSelect.jsp?country=" + country, true);
	request.send(null);
}

// Send form data via ajax to a url 
function doPost(url, formId) {
	var request = ajaxInit();
	request.onreadystatechange = function() {
		if(request.readyState == 4) {
			document.getElementById("content").innerHTML = request.responseText;
		}
	};

	var postData = getPostData(formId);
	request.open("post", "content/" + url, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.setRequestHeader("Content-length", postData.length);
	request.setRequestHeader("Connection", "close");	
	request.send(postData);
	
	alert("Thanks for choosing Travel Experts: " + postData);
}

// Builds string for AJAX get/post from form data 
function getPostData(formId) {
	var form = document.getElementById(formId);
	var dataString="";
	// Loop through elements   (name=value&name2=value2 etc)
	for(var i = 0; i < form.length; i++ ) {
		dataString += (encodeURI(form.elements[i].name) + "=" 
				+ encodeURI(form.elements[i].value) );
		if(i < form.length-1 ) dataString += "&";
	}
	return dataString;
}

function valPassword() {
	if(document.customer.custPassword.value != document.customer.confirmPassword.value) {
		alert("Passwords don't match!");
		document.customer.custPassword.focus();
		return false;
	}
	return true;
}
