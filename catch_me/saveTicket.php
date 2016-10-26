<?php
	require "connihack.php";
        $bus=$_GET["b"];
        $route=$_GET["r"];
	$origin=$_GET["o"];
	$destination=$_GET["d"];
	$amount=$_GET["a"];

        $ores= mysqli_query($conn,"SELECT id FROM places where name = '".$origin."' LIMIT 1");
        $dres= mysqli_query($conn,"SELECT id FROM places where name = '".$destination."' LIMIT 1");

        $oid = 0;
        $did = 0;

        while($row = mysqli_fetch_assoc($ores))
          $oid = $row['id'];

        while($row = mysqli_fetch_assoc($dres))
          $did = $row['id'];

	$mysql_qry="insert into tickets(bus,route,origin,destination,quantity,amount)values ($bus,$route,$oid,$did,1,$amount)";
	$result=mysqli_query($conn,$mysql_qry);
	echo $mysql_qry." Success";
?>							