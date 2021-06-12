<?php
if(isset($_FILES["uploaded_file"]["name"]));
{
 $name = $_FILES["uploaded_file"]["name"];
 $tmp_name = $_FILES["uploaded_file"]["tmp_name"];
 $err = $_FILES["uploaded_file"]["error"];

    if (!empty($name))
    {
        $location = "./assets/";
        if (!is_dir($location)) {
            mkdir($location);
        }
        if(move_uploaded_file($tmp_name,$location.$name)) {
            echo json_encode("File Berhasil di Upload");
        }
        else {
            echo json_encode("File gagal Di upload");
        }

    }
    else {
        echo json_encode("Please Select a file !");
    }

}
