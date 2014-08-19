<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['name']) && isset($_POST['des']) && isset($_POST['type']) && isset($_POST['price']) ) {
    
    $id = $_POST['id'];
	$name = $_POST['name'];
    $des = $_POST['des'];
	$type = $_POST['type'];
	$price = $_POST['price'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Update `psm`.`medicine` SET name = '$name' , type = '$type' , description = '$des' , price = '$price' where m_id = '$id'");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
        
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