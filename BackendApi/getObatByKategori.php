<?php

include_once("koneksi.php");

function set_response($Sucess, $message,$data) {

    $res = array(
        "isSuccess" => $Sucess,
        "message" => $message,
        "data" => $data
    );

    echo json_encode($res);
}


if( !empty($_GET["kategori"])) {

    $kategori = $_GET["kategori"];

    $query = "SELECT * FROM obat WHERE kategori = '$kategori'";

    $get = mysqli_query($connect,$query);

    $data = array();
    if (mysqli_num_rows($get) > 0) {
        while($row = mysqli_fetch_assoc($get)) {
            $data[] = $row;
        }
	set_response(true,"Data ditemukan",$data);
    }
    else {
        set_response(false,"Data tidak ditemukan",$data);
}
}