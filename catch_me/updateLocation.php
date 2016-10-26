<?php
	require "connihack.php";
	$bus=$_POST["bus"];
	$date_time=$_POST["date_time"];
	$longitude=$_POST["longitude"];
	$latitude=$_POST["latitude"];
	$is_started=$_POST["is_started"];
	$is_closed=$_POST["is_closed"];
	$mysql_qry="insert into bus_locations (bus,date_time,longitude,lattitude,is_started,is_closed)values ('$bus','$date_time','$longitude','$latitude','$is_started',$is_closed)";
        $mysql_qry2="insert into seating(bus,sensor1,sensor2)values ('$bus',0,0)";
	$result=mysqli_query($conn,$mysql_qry);
        mysqli_query($conn,$mysql_qry2);
	echo "Journey Started";
?>