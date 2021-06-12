<?php 

    include_once("koneksi.php");

    function set_response($isSucess,$message) {

        $result = array(
            "isSuccess" => $isSucess,
            "message" => $message
        );

        echo json_encode($result);
    }

    if (!empty($_POST["id_user"])) {
        $id = $_POST["id_user"];
        $query = "DELETE FROM users where id_user='$id'";
        
        $delete = mysqli_query($connect,$query);

        if($delete) {
            set_response(true,"Sucess Delete One");
        }
        else {
            set_response(false,"failed Delete One Data");
        }
    }