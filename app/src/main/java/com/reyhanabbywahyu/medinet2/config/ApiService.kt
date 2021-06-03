package com.reyhanabbywahyu.medinet2.config

import com.reyhanabbywahyu.medinet2.`class`.action.ResponseAction
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    //users

    @GET("getAllUser")
    fun getAllUserData() : Call<ResponseGetDataUser>

    @GET("getUserById")
    fun getUserById(@Query("id_user") id_user : String) : Call<ResponseGetDataUser>

    @GET("getUserByEmail.php")
    fun getUserByEmail(@Query("email") email : String) : Call<ResponseGetDataUser>
    //insert User
    @FormUrlEncoded
    @POST("insertUser.php")
    fun insertUser( @Field("nama") nama : String,
                    @Field("email") email : String,
                    @Field("password") password:String,
                    @Field("tanggalLahir") tanggalLahir : String) : Call<ResponseAction>

    //delete user
    @FormUrlEncoded
    @POST("deleteUser.php")
    fun deleteUser(@Field("id_user") id_user : String) : Call<ResponseAction>

    //update user
    @FormUrlEncoded
    @POST("updateUserBeratTinggi.php")
    fun updateUserBeratTinggi( @Field("id_user")  id_user : String,
                               @Field("tinggi") tinggi: Int,
                               @Field("berat") berat: Int) : Call<ResponseAction>

    @FormUrlEncoded
    @POST("updateUserAlamat.php")
    fun updateUserAlamat(@Field("id_user") id : String,
                         @Field("alamat") alamat : String) : Call<ResponseAction>
    @FormUrlEncoded
    @POST("updateUserBiodata.php")
    fun updateUserBiodata(@Field("id_user") id_user:String,
                          @Field("nama") nama : String) : Call<ResponseAction>

    @FormUrlEncoded
    @POST("updateUserPassword.php")
    fun updateUserPassword(@Field("id_user") id_user: String,
                           @Field("password") password: String) : Call<ResponseAction>

    //Obat
    @GET("getAllObat.php")
    fun getAllObat() : Call<ResponseGetDataObat>

    @GET("getObatByKategori.php")
    fun getObatByKategori(@Query("kategori") kategori : String) : Call<ResponseGetDataObat>

    @FormUrlEncoded
    @POST("updateBalance.php")
    fun updateBalance(@Field("id_user") id_user : String,
                      @Field("balance")balance : Double)      : Call<ResponseAction>



}