
<?php

    include_once("koneksi.php");

    $query = "SELECT * FROM users";

    $get = mysqli_query($connect,$query);

    $data = array();
    if (mysqli_num_rows($get) > 0) {
        while($row = mysqli_fetch_assoc($get)) {
            $data[] = $row;
        }
        set_response(true,"Data Ditemukan",$data);
    }
    else {
        set_response(false, "Data Tidak Ditemukan",$data);
    }

    function set_response($isSucess,$message,$data) {
        $result = array(
            "isSuccess" => $isSucess,
            "message" => $message,
            "data" => $data
        );
        echo json_encode($result);
    }