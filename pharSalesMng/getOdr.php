<?php


// array for JSON response
$response = array();


	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

 if(isset($_GET['area'])){
	$area = $_GET['area'];
	$response["area"] = array();
	$rank = "MPO";
    $result = mysql_query("Select * from `psm`.`employee` where area_id = '$area' and rank = '$rank'") or die(mysql_error());
	if (mysql_num_rows($result) > 0) {
		$response["success"] = 1;
		while($row = mysql_fetch_array($result)){
			$tmp = $row['id'];
			$res = mysql_query("Select * from `psm`.`order_entry` where mpo_id = '$tmp' and status ='0' ")or die(mysql_error());
			
			if(mysql_num_rows($res) > 0){
				while($rec = mysql_fetch_array($res)){
					$m = array();
					$m['id'] = $rec['id'];
					$tt = $m['id'];
					
					
					$rrr = mysql_query("SELECT SUM(amount) as san,pro_id FROM  `order_product` where odr_id = '$tt' group by pro_id");
					while($rrrr = mysql_fetch_array($rrr)){
						$tmp = $rrrr['pro_id'];
						$rt['$tmp'] = (int)$rrrr['san'];
						
					}
					
					$rr =  mysql_query("(Select * from `psm`.`medicine_area_stock` where md_id in(SELECT pro_id FROM  `order_product` where odr_id = '$tt') and area_id = '$area') ")or die(mysql_error());
					$flag = true;
					while($rrrrr = mysql_fetch_array($rr)){
						$tmp = $rrrrr['md_id'];
						if((int)$rt['$tmp'] > (int)$rrrrr['stock']) $flag= false;
					}
					$m['status'] = $flag;
					array_push($response["area"], $m);
				}
			}
		}
	}
    // echoing JSON response
    echo json_encode($response);
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}

?>