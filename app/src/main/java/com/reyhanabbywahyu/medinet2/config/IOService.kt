package com.reyhanabbywahyu.medinet2.config

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IOService {

    @Multipart
    @POST("/upload.php")
    fun uploadFile(@Part file : MultipartBody.Part) : Call<String>


}