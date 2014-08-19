<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    $result = mysql_query("Select * from `psm`.`order_entry` ") or die(mysql_error());

   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
      $response["message"] = $row['id'];
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No area found";

    // echo no users JSON
    echo json_encode($response);
}

?>