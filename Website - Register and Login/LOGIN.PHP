<?php
include("LOGIN.HTML");
$bool = 0;
$host = "localhost";
$username = ""; // INSERT WITH YOUR USERNAME OF THE CONNECTION
$password = ""; // INSERT WITH YOUR PASSWORD OF THE CONNECTION
$database = "CSC350P3";
$conn = mysqli_connect($host, $username, $password, $database);
    
if (!$conn){
    die("Connection failed: " . mysqli_connect_error());
}
    
if(isset($_REQUEST["userID"]))
{
    $sql = "SELECT USERID, PASSWORD from USERS ";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0){
        while($row = mysqli_fetch_assoc($result)) {
            if(strcmp($_REQUEST["userID"], $row["USERID"]) == 0
               && strcmp($_REQUEST["password"], $row["PASSWORD"]) == 0){
                $bool = 1;
                break;
            }
            else
                $bool = 0;
           
        }
    }
    if($bool == 1)
        echo "USER SUCCESSFULLY AUTHENTICATED";
    else
        echo "INVALID USERID OR PASSWORD - TRY AGAIN OR REGISTER";
        
}

$conn->close();
?>
</body>
</html>
