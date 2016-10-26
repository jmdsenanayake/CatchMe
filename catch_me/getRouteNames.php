<?php
	require "connihack.php";
	$rnumber=$_GET["rnum"];
	//query for retrieving all places
	$mysql_qry="SELECT p.name as origin, pd.name as destination FROM routes r LEFT JOIN places p ON p.id = r.origin_place LEFT JOIN places pd ON pd.id = r.destination_place WHERE r.route_number = ". $rnumber;
			
	$rows = array();
	$sth = mysqli_query($conn, $mysql_qry);
	
	while($r = mysqli_fetch_assoc($sth))
		$rows[] = $r;
	
	print '{ "places": '. json_encode($rows) . ' }';
?>