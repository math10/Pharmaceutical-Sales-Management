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

    
    $result = mysql_query("Select Sum(amount) as san,pro_id from `order_product` where odr_id = '$id' group by pro_id");

   $response['list'] = array();
    if ($result) {
        // successfully updated
       while($res = mysql_fetch_array($result)){
		$tmp = array();
		$tmp['amount'] = $res['san'];
		$t = $res['pro_id'];
		$r = mysql_query("Select name from `medicine` where m_id = '$t'");
		$rr = mysql_fetch_array($r);
		
		$tmp['pro_id'] = $r['name'];
		array_push($response["list"], $tmp);
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