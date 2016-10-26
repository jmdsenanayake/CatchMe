<?php
	require "connihack.php";
	$user_name=$_POST["user_name"];
	$user_pass=$_POST["password"];
	$mysql_qry="insert into employee_data (username,password)values ('$user_name','$user_pass')";
	$result=mysqli_query($conn,$mysql_qry);
	echo "data entered";
?>