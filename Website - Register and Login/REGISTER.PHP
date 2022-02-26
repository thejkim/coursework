<?php
include("REGISTER.HTML");
// Do whatever REGISTER.PHP should do here.
// I'm done on password part BUT USERID PART IS NEEDED TO BE DONE WITH DB.

/**
$bool: use this boolVal to redirect to two different pages
Assign 0 if the user is not authenticated, means invalid password and user should re-enter the form.
      if bool = 0, it comes back to REGISTRATION.PHP (what html calls as action)
Assign 1 if the user is authenticated, means valid password and user should be redirected to INFORMATION SCREEN.
      if bool = 1, it redirects user to INFORMATION.PHP
**/
session_start();
$boolp = intval(false);
$boolu = intval(false);
$cnt = 0;
$host = "localhost";
$username = ""; // INSERT WITH YOUR USERNAME OF THE CONNECTION
$password = ""; // INSERT WITH YOUR PASSWORD OF THE CONNECTION
$database = "CSC350P3";
$pw = NULL;
$conn = mysqli_connect($host, $username, $password, $database);

$boolArray = array("length"=>intval(false), "uppercase"=>intval(false), "lowercase"=>intval(false), "number"=>intval(false), "special"=>intval(false));
        
if (!$conn){
    die("Connection failed: " . mysqli_connect_error());
}

if(!empty($_REQUEST['password']) && !empty($_REQUEST["userID"])) {
  $pw = $_REQUEST['password'];
	//password is at least 8 characters long.
	if(strlen($_REQUEST['password']) >= 8) {
		$cnt = $cnt + 1;
		$boolArray["length"] = intval(true);
	}
	//contains Upper case letter
    if(preg_match('/([A-Z])/', "$pw", $matches, PREG_OFFSET_CAPTURE)) {
      $cnt = $cnt + 1;
      $boolArray["uppercase"] = intval(true);
    } //contains Lower case letter
    if(preg_match('/([a-z])/', "$pw", $matches, PREG_OFFSET_CAPTURE)) {
      $cnt = $cnt + 1;
      $boolArray["lowercase"] = intval(true);
    } //contains number
    if(preg_match('/([0-9])/', "$pw", $matches, PREG_OFFSET_CAPTURE)) {
      $cnt = $cnt + 1;
      $boolArray["number"] = intval(true);
    } //contains special character
    if(preg_match("/[~!@#$%^&*()_?+><]/", $pw)) {
      $cnt = $cnt + 1;
      $boolArray["special"] = intval(true);
    }

    //check if the password is strong or not.
    if ($boolArray["length"] == intval(true) && $boolArray["uppercase"] == intval(true) 
	&& $boolArray["lowercase"] == intval(true) && $boolArray["number"] == intval(true) 
	&& $boolArray["special"] == intval(true)){
		$boolp = intval(true);
	}


    
      
    $sql = "SELECT USERID from USERS ";
    $result = mysqli_query($conn, $sql);
    if(mysqli_num_rows($result) == 0)
          $boolu = intval(true);
    else if (mysqli_num_rows($result) > 0){
        while($row = mysqli_fetch_assoc($result)){
            if(strcmp($_REQUEST["userID"], $row["USERID"]) == 0){
                $boolu = intval(false);
                echo "USER ID IS TAKEN - ";
            }
            else
                $boolu = intval(true);
        }
    }

	if(empty($_REQUEST["userID"])){
		$boolu = intval(false);
	}

	if($boolp == intval(false)){
		 echo "INVALID USERID OR PASSWORD"."<br><br>";
		 foreach($boolArray as $categories => $categories_value) {
		    if($categories_value == intval(false)) {
			if("$categories" == "length")
				echo "Password is less than 8 characters."."<br>";
            if("$categories" == "uppercase")
              echo "Password does not contain uppercase characters"."<br>";
            if("$categories" == "lowercase")
              echo "Password does not contain lowercase characters"."<br>";
            if("$categories" == "number")
              echo "Password does not contain numbers"."<br>";
            if("$categories" == "special")
              echo "Password does not contain special characters"."<br>";
			}
		}
	}

	if($boolu == intval(false)){
		echo "INVALID USERID OR PASSWORD"."<br><br>";
		if (empty($_REQUEST["userID"]))
	  	  echo "UserID must be filled out"."<br>";
	}
    /** PHP REDIRECTION PART! **/ 
	else {
        $_SESSION["userid"] = $_REQUEST["userID"];
        $_SESSION["password"] = $_REQUEST["password"];
        header("Location: INFO.PHP");
    }


	   
	

  
}




?>
</body>
</html>