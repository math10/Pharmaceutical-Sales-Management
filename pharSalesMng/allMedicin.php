<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    
    $result = mysql_query("Select * from `psm`.`medicine`") or die(mysql_error());;

   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["medicine"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $medicine = array();
        $medicine['m_id'] = $row['m_id'];
		$medicine['name'] = $row['name'];

        // push single product into final response array
        array_push($response["medicine"], $medicine);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}

?>