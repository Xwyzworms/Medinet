<?php 


include_once("koneksi.php");

function set_response($isSuccess, $message,$data) {

    $res = array(
        "isSuccess" => $isSuccess,
        "message" => $message,
        "data" => $data
    );

    echo json_encode($res);

}

$query = "SELECT * from obat";

$get = mysqli_query($connect,$query);

$data = array();

if (mysqli_num_rows($get) > 0) {
    while($row = mysqli_fetch_assoc($get)) {
            $data[] = $row;

    }
    set_response(true,"Data Obat Ditemukan",$data);

}
else {
    set_response(false,"Data Obat tidak ditemukan",$data);
    
}
