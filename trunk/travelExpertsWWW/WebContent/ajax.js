/**
 *	Some JavaScript functions 
 *  - Will Dixon
 */

function reconnect() {
	var SupportApplet = document.applets.SupportApplet;
	SupportApplet.reconnect();
}

function getRequestObject() {
  if (window.ActiveXObject) {
    return(new ActiveXObject("Microsoft.XMLHTTP"));
  } else if (window.XMLHttpRequest) {
    return(new XMLHttpRequest());
  } else {
    return(null);
  }
}

function ajaxAlert(address) {
  request = getRequestObject();
  request.onreadystatechange = 
  function() { showResponseAlert(request); };
  request.open("GET", address, true);
  request.send(null);
}

function ajaxAlert(address) {
	  request = getRequestObject();
	  request.onreadystatechange = 
	  function() { showResponseAlert(request); };
	  request.open("GET", address, true);
	  request.send(null);
}

function showResponseAlert(request) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    alert(request.responseText);
  }
}


function customerDetails() {
}
