<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['name']) && isset($_POST['area']) && isset($_POST['id']) ) {
    
    $id = $_POST['id'];
	$name = $_POST['name'];
    $area = $_POST['area'];
	$rank = "SMG";
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Insert into `psm`.`employee` (id,name ,  area_id ,password, rank ) VALUES('$id','$name','$area','$id','$rank')");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Add Sales Managers successfully.";
        
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