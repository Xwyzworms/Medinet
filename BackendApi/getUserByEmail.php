<?php

    include_once("koneksi.php");
    if(!empty($_GET["email"])) {
        $email = $_GET["email"];
        
        $query = "SELECT * FROM users WHERE email='$email'";
	
        $get = mysqli_query($connect, $query);
        $data = array();
        if(mysqli_num_rows($get) > 0) {
            while($row = mysqli_fetch_assoc($get)) {
                $data[] = $row;
            }

            set_response(true,"Data Ditemukan",$data);
        }
	else {
	    set_response(false,"Data Tidak ditemukan",$data);	
	}

    }
        
    
    function set_response($isSucces,$message,$data) {
        $result = array(
            "isSuccess" => $isSucces,
            "message" => $message,
            "data" => $data
        );
        echo json_encode($result);
    }