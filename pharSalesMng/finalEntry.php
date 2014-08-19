<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['amount'])  ) {
    
    $id = $_POST['id'];
	$name = $_POST['amount'];
    
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Update `psm`.`order_entry` SET totalCost = '$name' where id = '$id'");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "successfully added.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>