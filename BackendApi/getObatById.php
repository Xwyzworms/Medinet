<?php

include_once("koneksi.php");

function set_response($isSucess, $message,$data) {

    $res = array(
        "isSuccess" => $isSucess,
        "message" => $message,
        "data" => $data
    );

    echo json_encode ($res);
}
    $data = array();
if( !empty($_GET["id_obat"] )) {  

    $id = $_GET["id_obat"];
    echo $id;
    $query = "SELECT * FROM obat where id_obat = '$id'";
    
    $get = mysqli_query($connect, $query);


    if(mysqli_num_rows($get) > 0) {
            while ($row = mysqli_fetch_assoc($get)) {
                $data[] = $row;
            }
        set_response(true,"Data ditemukan",$data);            
    }
    

}
else {
    set_response(false,"data tidak ditemukan",$data);
}