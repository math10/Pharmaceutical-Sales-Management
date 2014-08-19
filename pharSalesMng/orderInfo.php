<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_GET['id']) ) {
    
    $id = $_GET['id'];
	
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Select * from `order_entry` where id = '$id'");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "MPO info successfully updated.";
        $res = mysql_fetch_array($result);
		$response["tot"] = $res['totalCost'];
		$tmp = $res['customer_id'];
		$result = mysql_query("Select * from `customer` where id = '$tmp'");
		$res = mysql_fetch_array($result);
		$response["name"] = $res['name'];
		$response["add"] = $res['address'];
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