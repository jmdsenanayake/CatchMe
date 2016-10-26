<?php
	require "connihack.php";
	$busNo=$_POST["busNo"];	
	$sensorNo=$_POST["sensorNo"];

	if($sensorNo=="1"){
		$mysql_qry1="select record_id from seating where sensor1=0 and sensor2=0 and bus_no='$busNo'";
		$result1=mysqli_query($conn,$mysql_qry1);
		if(mysqli_num_rows($result1)>0){
			while($row1 = $result1->fetch_assoc()) {
				$recordid=$row1["record_id"];

				$mysql_qry2="update seating set sensor1=1 where record_id='$recordid'";	
				mysqli_query($conn,$mysql_qry2);			
			}		
		}
		else{
			$mysql_qry3="select record_id from seating where sensor1=0 and sensor2=1 and bus_no='$busNo'";
			$result2=mysqli_query($conn,$mysql_qry3);
			if(mysqli_num_rows($result2)>0){
				while($row2 = $result2->fetch_assoc()) {
					$recordid=$row2["record_id"];			
					$mysql_qry4="update bus_locations set crowd=crowd-1 where bus='$busNo'";	
					mysqli_query($conn,$mysql_qry4);
					$mysql_qry5="update seating set sensor1=0, sensor2=0 where record_id='$recordid'";
					mysqli_query($conn,$mysql_qry5);			
				}		
			}
		}
	}
	
	else if($sensorNo=="2"){
		$mysql_qry1="select record_id from seating where sensor1=0 and sensor2=0 and bus_no='$busNo'";
		$result1=mysqli_query($conn,$mysql_qry1);
		if(mysqli_num_rows($result1)>0){
			while($row1 = $result1->fetch_assoc()) {
				$recordid=$row1["record_id"];
				$mysql_qry2="update seating set sensor2=1 where record_id='$recordid'";	
				mysqli_query($conn,$mysql_qry2);			
			}		
		}
		else{
			$mysql_qry3="select record_id from seating where sensor1=1 and sensor2=0 and bus_no='$busNo'";
			$result2=mysqli_query($conn,$mysql_qry3);
			if(mysqli_num_rows($result2)>0){
				while($row2 = $result2->fetch_assoc()) {
					$recordid=$row2["record_id"];			
					$mysql_qry4="update bus_locations set crowd=crowd+1 where bus='$busNo'";	
					mysqli_query($conn,$mysql_qry4);
					$mysql_qry5="update seating set sensor1=0, sensor2=0 where record_id='$recordid'";
					mysqli_query($conn,$mysql_qry5);			
				}		
			}
		}
	}
?>			