<?php


// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['name']) && isset($_POST['address']) ) {
    
    
	$name = $_POST['name'];
    $add = $_POST['address'];
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    $result = mysql_query("Select * from `psm`.`medicine` ") or die(mysql_error());
		$cnt = mysql_num_rows($result);
		$cnt++;
    $result = mysql_query("Insert into `psm`.`area` (name ,  address  ) VALUES('$name','$add')");

   
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Add Area successfully.";
		$result = mysql_query("Select * from `psm`.`area` ") or die(mysql_error());
		if (mysql_num_rows($result) > 0) {
			 while ($row = mysql_fetch_array($result)) {
				$cnt = $row['area_id'];
			}
		  }
        $result = mysql_query("Select * from `psm`.`medicine` ") or die(mysql_error());
		  if (mysql_num_rows($result) > 0) {
			 while ($row = mysql_fetch_array($result)) {
				$r  = $row['m_id']; 
				$tmp = mysql_query("Insert into `psm`.`medicine_area_stock`(md_id,area_id,stock) VALUES('$r','$cnt','0')");
			}
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