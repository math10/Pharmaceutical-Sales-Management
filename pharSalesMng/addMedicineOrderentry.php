<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['odrId']) && isset($_POST['proId']) && isset($_POST['amount']) ) {
    
    $odrId = $_POST['odrId'];
	$proId = $_POST['proId'];
	$amount = $_POST['amount'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Insert into `psm`.`order_product` (odr_id,pro_id,amount) VALUES('$odrId','$proId','$amount')");
	
	
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Add medicine order successfully.";
       
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