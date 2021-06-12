<?php

require_once "koneksi.php";

global $connect;
$upload_path = "uploads/";
$server_ip = gethostbyname(gethostname());
$upload_url = 'http://'.$server_ip.'/BackendApi/'.$upload_path;

$response = array();
if($_SERVER["REQUEST_METHOD"] == 'POST') {


	if($_POST["id_user"] != "") {
		$caption = $_POST["id_user"];
		$fileinfo = pathinfo($_FILES["image"]["name"]); // Getting file information
		$extension = $fileinfo["extension"]; // Getting the file extension
		$fileName = getFileName();
		$file_url = $upload_url . $fileName . '.' . $extension; // file url to store in database
		$file_path = $upload_path . $fileName . "." . $extension;
		$img_name = getFileName() . "." . $extension;

		try {
			move_uploaded_file($_FILES["image"]["tmp_name"],$file_path); // Saving the file to uploads folder

			$sqlQuery = "UPDATE users set photo_url = '$file_url' WHERE id_user = '$caption'";
			if(mysqli_query($connect,$sqlQuery)) {
				$response['error'] = false;
				$response["photo_url"] = $file_url;
			}

		} catch(Exception $e) {
			$response["error"] = true;
			$response["message"] = $e->getMessage();
		}

		echo json_encode($response);

		mysqli_close($connect);

	}
	else {
		$response["error"] = true;
		$response["message"] = "Cobak Cari File dlu";
	}
}
function generateRandomString($length = 10) {
	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$charactersLength = strlen($characters);
	$randomString = '';
	for ($i = 0; $i < $length; $i++) {
	    $randomString .= $characters[rand(0, $charactersLength - 1)];
	}
	echo $randomString;
	return $randomString;
    }

function getFileName() {
	global $connect;
		return generateRandomString(5);
	}


