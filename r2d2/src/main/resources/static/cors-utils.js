//Detect browser support for CORS
if ('withCredentials' in new XMLHttpRequest()) {
	/* supports cross-domain requests */
	console.log("CORS supported (XHR)");
} else {
	if (typeof XDomainRequest !== "undefined") {
		// Use IE-specific "CORS" code with XDR
		console.log("CORS supported (XDR)");
	} else {
		// Time to retreat with a fallback or polyfill
		console.log("No CORS Support!");
	}
}

var getXHR = function() {
	try {
		return new XMLHttpRequest();
	} catch (e) {
	}
	try {
		return new ActiveXObject("Msxml2.XMLHTTP.6.0");
	} catch (e) {
	}
	try {
		return new ActiveXObject("Msxml2.XMLHTTP.3.0");
	} catch (e) {
	}
	try {
		return new ActiveXObject("Microsoft.XMLHttp");
	} catch (e) {
	}
	console.err("Could not find XMLHttpRequest");
};
var makeRequest = function(uri, data, callbackfunction) {
	// make the actual XMLHttpRequest
	var xhr = getXHR();
	if ('withCredentials' in xhr)
		console.log("Using XMLHttpRequest2 to make AJAX requests");
	xhr.open("POST", uri, true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200 || xhr.status === 304) {
				var response = JSON.parse(xhr.responseText);
				callbackfunction(response);
			}
		} else
			console.log("Response recieved with status " + xhr.status);
	};
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.setRequestHeader("Accept", "application/json");
	xhr.send(JSON.stringify(data));
};