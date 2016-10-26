<?php
	require "connihack.php";
	
	//query for retrieving all places
	$mysql_qry="SELECT `name` FROM places";
			
	$rows = array();
	$sth = mysqli_query($conn, $mysql_qry);
	
	while($r = mysqli_fetch_assoc($sth))
		$rows[] = $r;
	
	print '{ "places": '. json_encode($rows) . ' }';
?>