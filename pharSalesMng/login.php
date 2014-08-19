<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['password']) && isset($_POST['rank']) ) {
    
    $id = $_POST['id'];
    $password = $_POST['password'];
	$rank = $_POST['rank'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Select * from `psm`.`employee` where id = '$id' and password = '$password' and rank = '$rank'");

   
    if (mysql_num_rows($result) == 1) {
        
        $row = mysql_fetch_array($result);
		if($row['password'] == $password){ 
			$response["success"] = 1;
			if($rank != "ADM")$response['area'] = $row['area_id'];
			else $response['area'] = "";
        }else{
			$response["success"] = 0;
			$response["message"] = "wrong id or password";
		}
		// echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "wrong id or password";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>