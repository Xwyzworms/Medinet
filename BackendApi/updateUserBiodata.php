<?php

include_once("koneksi.php");

$nama = "unknown";

function set_response($isSuccess,$message) {

    $res = array(
        "isSuccess" =>$isSuccess,
        "message" => $message
    ); 
    echo json_encode($res);
}
if ($_POST["nama"]) {

    $nama = $_POST["nama"];
    $id = $_POST["id_user"];

    $query = "UPDATE users set nama = '$nama' where id_user = '$id'";
    
    set_response(true,"Berhasil Update Nama");

}
else {
    set_response(false,"Gagal Update Nama");
}