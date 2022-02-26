<?php
include("INFO.HTML");
// Do whatever INFORMATION.PHP should do here.

//IF the user completes entering,
//echo "USER REGISTERED SUCCESSFULLY";

//IF NOT,
    session_start();
    $host = "localhost";
    $username = ""; // INSERT WITH YOUR USERNAME OF THE CONNECTION
    $password = ""; // INSERT WITH YOUR PASSWORD OF THE CONNECTION
    $database = "CSC350P3";
    $cnt = 0;
    $conn = mysqli_connect($host, $username, $password, $database);
    
    if (!$conn){
        die("Connection failed: " . mysqli_connect_error());
    }
    
    $userid = $_SESSION["userid"];
    $password = $_SESSION["password"];
    
    if(!empty($_REQUEST["firstName"])){
        $cnt = $cnt + 1;
        
    }
    if(!empty($_REQUEST["lastName"])){
        $cnt = $cnt + 1;
        
    }
    if(!empty($_REQUEST["city"])){
        $cnt = $cnt + 1;
        
    }
    if(!empty($_REQUEST["state"])){
        $cnt = $cnt + 1;
        
    }
    if(!empty($_REQUEST["zipCode"])){
        $cnt = $cnt + 1;
        
    }
    if($cnt == 5){
        $fn = $_REQUEST["firstName"];
        $ln = $_REQUEST["lastName"];
        $ct = $_REQUEST["city"];
        $st = $_REQUEST["state"];
        $zc = $_REQUEST["zipCode"];
        
        $sql = "INSERT INTO USERS (USERID, PASSWORD, FIRSTNAME, LASTNAME, CITY, STATE, ZIPCODE) VALUES ('".$userid."', '".$password."', '".$fn."', '".$ln."', '".$ct."', '".$st."', '".$zc."');";
        $result = mysqli_query($conn, $sql);
        if(!$result){
            echo "DATABASE ERROR - ";
        }
        else{
            echo "USER REGISTERED SUCCESSFULLY";
        }
        
    }
    
    else{
        echo "ALL FIELDS MUST BE FILLED OUT";
    }
    
    
$conn->close();
?>
</body>
</html>
