<?php
	require "connihack.php";
	$busNo=$_POST["busNo"];	
	$ratingVal=$_POST["ratingVal"];
	
	$mysql_qry2="update busses set rating=(rating+$ratingVal)/2 where `number`='$busNo'";	
	mysqli_query($conn,$mysql_qry2);
?>