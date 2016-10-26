<?php
	require "connihack.php";
	$busName=$_POST["busName"];
	$busNo=$_POST["busNo"];
	$route=$_POST["route"];
	$ownerName=$_POST["ownerName"];
	$ownerNIC=$_POST["ownerNIC"];
	$ownerContact=$_POST["ownerContact"];
	$uname=$_POST["uname"];
	$pword=$_POST["pword"];
	$busType=$_POST["busType"];
	$seats=$_POST["seats"];

	$mysql_qry="insert into busses (name,number,primary_route,owner_name,owner_nic,owner_contact,uname,pword,bus_type,seating_capacity,approval)values ('$busName','$busNo','$route','$ownerName','$ownerNIC','$ownerContact','$uname','$pword','$busType','$seats',0)";
	$result=mysqli_query($conn,$mysql_qry);
	echo "Registration Successful";
?>

