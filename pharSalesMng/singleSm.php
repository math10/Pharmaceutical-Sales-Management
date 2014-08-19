<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

 if(isset($_GET['id'])){
	$id = $_GET['id'];
    $result = mysql_query("Select * from `psm`.`employee` where id = '$id'") or die(mysql_error());

   
    if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["employee"] = array();
    
   $row = mysql_fetch_array($result);
        // temp user array
        $employee = array();
        $employee['m_id'] = $row['id'];
		$employee['name'] = $row['name'];
		$employee['area_id'] = $row['area_id'];
		$employee['area'] = $row['area_id'];
		$id = $employee['area'];
		$result = mysql_query("Select * from `psm`.`area` where area_id = '$id'") or die(mysql_error());
		$row = mysql_fetch_array($result);
		$employee['area'] = $row['name'];
        // push single product into final response array
        array_push($response["employee"], $employee);
    
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
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}

?>