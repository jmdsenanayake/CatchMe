<?php
	require "connihack.php";
	$o_place=$_GET["o_place"];
	$d_place=$_GET["d_place"];
	
	//query for getting location details of the origin and the destination
	$osql = "SELECT * FROM places WHERE `name` = '".$o_place."'";
	$dsql = "SELECT * FROM places WHERE `name` = '".$d_place."'";
	
	$o_long = "";
	$o_lat = "";
	$d_long = "";
	$d_lat = "";
	
	$oquery = mysqli_query ( $conn , $osql );			
	$dquery = mysqli_query ( $conn , $dsql );			
									
	while ( $row = mysqli_fetch_assoc ( $oquery ) )
	{
		$o_long = $row['longitude'];
		$o_lat = $row['lattitude'];
	}
	
	while ( $row = mysqli_fetch_assoc ( $dquery ) )
	{
		$d_long = $row['longitude'];
		$d_lat = $row['lattitude'];
	}
	
	//query for retrieving current bus locations
	$mysql_qry="SELECT b.name as busName, b.number as busNumber, b.bus_type as btype, l.longitude as longitude, l.lattitude as lattitude, IFNULL(b.seating_capacity,0) as capacity, IFNULL(l.crowd,0) as crowd, b.rating as rating
				FROM bus_locations l
				LEFT JOIN busses b ON b.id = l.bus
				LEFT JOIN routes r ON r.id = b.primary_route
				LEFT JOIN places po ON  po.id = r.origin_place
				LEFT JOIN places pd ON pd.id = r.destination_place
				WHERE po.name = '".$o_place."' AND pd.name = '".$d_place."' AND is_started = TRUE AND is_closed = FALSE";
			
	$rows = array();
	$sth = mysqli_query($conn, $mysql_qry);
	
	while($r = mysqli_fetch_assoc($sth))
		$rows[] = $r;
	
	print '{ "o_long": '.$o_long.', "o_lat": '.$o_lat.',"d_long": '.$d_long.', "d_lat": '.$d_lat.', "results": '. json_encode($rows) . ' }';
?>