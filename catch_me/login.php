<?php
	require "connihack.php";
	$user_name=$_POST["user_name"];
	$user_pass=$_POST["password"];
	$mysql_qry="select id from busses where uname = '$user_name' and pword = '$user_pass' and approval=1";
	$result=mysqli_query($conn,$mysql_qry);
	if(mysqli_num_rows($result)>0){
		while($row = $result->fetch_assoc()) {
			echo $row["id"];
    }		
	}
	else{
		echo "Invalid Login";
	}
?>