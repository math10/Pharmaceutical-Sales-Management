<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    $rank = "MPO";
    $result = mysql_query("Select * from `psm`.`employee` where rank = '$rank'") or die(mysql_error());;

   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["employee"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $employee = array();
        $employee['m_id'] = $row['id'];
		$employee['name'] = $row['name'];

        // push single product into final response array
        array_push($response["employee"], $employee);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No MPO found";

    // echo no users JSON
    echo json_encode($response);
}

?>