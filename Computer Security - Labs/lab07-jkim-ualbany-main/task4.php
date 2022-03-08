<?php
   function getDB() {
     $dbhost="localhost";
     $dbuser="root";
     $dbpass="seedubuntu";
     $dbname="Users";

     // Create a DB connection
     $conn = new mysqli($dbhost, $dbuser, $dbpass, $dbname);
     if ($conn->connect_error) {
         die("Connection failed: " . $conn->connect_error . "\n");
     }
     return $conn;
   }

   $eid = $_GET['EID'];
   $pwd = $_GET['Password'];

   $conn = new mysqli("localhost", "root", "seedubuntu", "Users");
   $sql = "SELECT Name, Salary, SSN
           FROM credential
           WHERE eid= ? and password=?";              
   if ($stmt = $conn->prepare($sql)) {               
      $stmt->bind_param("ss", $eid, $pwd);          
      $stmt->execute();                             
      $stmt->bind_result($name, $salary, $ssn);     
      while ($stmt->fetch()) {                      
         printf ("%s %s %s\n", $name, $salary, $ssn);
      }
      $result->free();
   }
   $conn->close();
?>

