<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['odrId']) && isset($_POST['cusId']) && isset($_POST['mpoId']) ) {
    
    $odrId = $_POST['odrId'];
	$cusId = $_POST['cusId'];
	$mpoId = $_POST['mpoId'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Insert into `psm`.`order_entry` (id,customer_id,mpo_id,status) VALUES('$odrId','$cusId','$mpoId','0')");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Add oRder successfully.";
       
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