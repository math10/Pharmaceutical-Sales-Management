<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

 if(isset($_GET['id']) && isset($_GET['area']) && isset($_GET['amount'])){
	$id = $_GET['id'];
	$area = $_GET['area'];
    $result = mysql_query("Select * from `psm`.`medicine_area_stock` where md_id = '$id' and area_id = '$area'") or die(mysql_error());
   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
		$response["medicine"] = array();
    
		$row = mysql_fetch_array($result);
        $tmp = (int)$row['stock'];
		$tmp = $tmp + (int)$_GET['amount'];
		$result = mysql_query("Update `psm`.`medicine_area_stock` Set stock = '$tmp' where md_id = '$id' and area_id = '$area'") or die(mysql_error());
		if($result)$response["success"] = 1;
		else {
			$response["success"] = 0;
		}
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
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