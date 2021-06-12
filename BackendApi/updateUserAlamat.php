<?php

include_once("koneksi.php");

function response($success, $message) {
    echo json_encode(array(
        "success" => $success,
        "message" => $message
    ));
}
if(!empty($_POST["id_user"]) && !empty($_POST["alamat"])) {
    $alamat = $_POST["alamat"];
    $id = $_POST["id_user"];

    $query = "UPDATE users set alamat = '$alamat' where id_user = '$id'";

    $res = mysqli_query($connect,$query);

    if($res) {
        response(true,"Sucessfully Update Alamat");
    }
    else {
        response(false,"Failed Update Alamat");
    }
}
else {
    response(false, "No data Found");
}