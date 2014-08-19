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

    
    $result = mysql_query("Select * from `order_entry` where status = '0' or status = '1'");

   
    if ($result) {
        // successfully updated
		$response['order'] = array();
		while($row = mysql_fetch_array($result)){
			$mas = array();
			$mas['id'] = $row['id'];
			$mas['tot'] = $row['totatlcost'];
			$mas['status'] = $row['status'];
			$t = $row['customer_id'];
			$r = mysql_query("Select * from `customer` where mpo_id = '$t' ");
			$rr = mysql_fetch_array($r);
			$mas['name'] = $rr['name'];
			$mas['add'] = $rr['address'];
			array_push($response["order"], $mas);
		}
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