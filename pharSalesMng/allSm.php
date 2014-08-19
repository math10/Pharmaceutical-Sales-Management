<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    $rank = "SMG";
    $result = mysql_query("Select * from `psm`.`employee` where rank = '$rank'") or die(mysql_error());;

   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["smg"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $smg = array();
        $smg['m_id'] = $row['id'];
		$smg['name'] = $row['name'];

        // push single product into final response array
        array_push($response["smg"], $smg);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Sales Manager found";

    // echo no users JSON
    echo json_encode($response);
}

?>