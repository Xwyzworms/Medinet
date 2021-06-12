<?php

    include_once("koneksi.php");

    if(
        !empty($_POST["nama"]) &&
        !empty($_POST["email"])&&
        !empty($_POST["password"])&&
        !empty($_POST["tanggalLahir"])){
            
            $nama = $_POST["nama"];
            $email = $_POST["email"];
            $password = $_POST["password"];
            $tanggalLahir = $_POST["tanggalLahir"];

            $query = "INSERT INTO users(nama,email,password,tanggalLahir) values ('$nama','$email','$password','$tanggalLahir')";
            
            $insert = mysqli_query($connect,$query);
            if($insert) {
                set_response(true,"Succes Insert Data");
            }
            else {
                set_response(false, "Failed Insert Data" );
            }
        }
        else {
            set_response(false,"Null Value Detected");
        }

    
function set_response($isSucess, $message) {

    $res = array(
        "isSuccess" => $isSucess,
        "message" => $message
    );
    echo json_encode($res);

}