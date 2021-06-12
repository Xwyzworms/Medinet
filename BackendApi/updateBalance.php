<?php

include_once("koneksi.php");



function response($success,$message) {
    echo json_encode(array(
        "success" => $success,
        "message" => $message
    ));
}

if(($_POST["id_user"] != "") && ($_POST["balance"] != "")) {

    $id = $_POST["id_user"];
    $bln = $_POST["balance"];

    $query = "UPDATE users SET balance = '$bln' where id_user = '$id'";
    $res = mysqli_query($connect,$query);
    if($res) {
        response(true,"success Updating Balance");
    }
    else {
        response(false,"Failed Updating Balance");
    }
}
else {
    response(false,"Error getting data");
}