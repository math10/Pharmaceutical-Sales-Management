<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) ) {
    
    $id = $_POST['id'];
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Delete from `psm`.`employee` where id = '$id'");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Mpo successfully deleted.";
        
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