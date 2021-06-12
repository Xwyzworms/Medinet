<?php

include_once("koneksi.php");

function set_response($isSucess, $message) {

    $res = array (

        "isSuccess" => $isSucess,
        "message" => $message 
    );

    echo json_encode($res);


}

$tinggi = 0;
$berat = 0;

if( !empty($_POST["tinggi"]) && !empty($_POST["berat"]) ) 
{
    $id = $_POST["id_user"];
    $tinggi = $_POST["tinggi"];
    $berat = $_POST["berat"];

    $query = "UPDATE users set tinggi = '$tinggi', berat = '$berat' where id_user ='$id'";

    if (mysqli_query($connect,$query)) {
        set_response(true,"Tinggi dan Berat Berhasil Di Update");
    }
    else {
        set_response(false,"Gagal update Data Tinggi dan berat");
    }
}
else if (!empty($_POST["tinggi"])) {

    $id = $_POST["id_user"];
    $tinggi = $_POST["tinggi"];

    $query = "UPDATE users set tinggi = '$tinggi' where id_user ='$id'";

    if (mysqli_query($connect,$query)) {
        set_response(true,"Tinggi Berhasil Di Update");
    }
    else {
        set_response(false,"Gagal update Data Tinggi");
    }



}
else if (!empty($_POST["berat"])) {

    $id = $_POST["id_user"];
    $berat = $_POST["berat"];

    $query = "UPDATE users set berat= '$berat' where id_user ='$id'";

    if (mysqli_query($connect,$query)) {
        set_response(true,"Berat Berhasil Di Update");
    }
    else {
        set_response(false,"Gagal update Data berat");
    }

}
else {
     set_response(false,"Gagal Update TOTAL BOSS");
}