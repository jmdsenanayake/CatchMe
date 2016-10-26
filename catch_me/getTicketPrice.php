<?php
	require "connihack.php";
	$rnumber=$_GET["rnum"];

	$mysql_qry="SELECT op.name as origin, dp.name as destination, r.normal_ticket, r.semilux_ticket, r.lux_ticket 
FROM `routes` r
left join places op on op.id = r.origin_place
left join places dp on dp.id = r.destination_place";
			
	$rows = array();
	$sth = mysqli_query($conn, $mysql_qry);
	
	while($r = mysqli_fetch_assoc($sth))
		$rows[] = $r;
	
	print '{ "ticket_prices": '. json_encode($rows) . ' }';
?>