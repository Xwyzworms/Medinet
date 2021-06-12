<?php

include_once("koneksi.php");

$password = "";

function set_response($isSuccess,$message){

    $res = array(
        "isSuccess" => $isSuccess,
        "message" => $message
    );
    
    echo json_encode($res);
}
if(!empty($_POST["password"])) {

    $password = $_POST["password"];
    $id = $_POST["id_user"];

    $query = "UPDATE users set password = '$password' where id_user = '$id'";
    set_response("Success","Update Password Berhasil");
} 
else {

    set_response("Failed", "Update Password Failed");
}