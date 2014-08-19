<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['name']) && isset($_POST['area'])  ) {
    
    $id = $_POST['id'];
	$name = $_POST['name'];
    $area = $_POST['area'];
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Update `psm`.`employee` SET name = '$name' , area_name = '$area'  where id = '$id'");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Sales Managers info successfully updated.";
        
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