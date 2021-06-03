package com.reyhanabbywahyu.medinet2.`class`

import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class UserResponse(
        @field:SerializedName("id_user")
        var id_user : String = "0",

        @field:SerializedName("nama")
        var nama : String = "unknown",

        @field:SerializedName("email")
        var email : String ?= null,

        @field:SerializedName("tanggalLahir")
        var tanggalLahir : Date ?= null,

        @field:SerializedName("password")
        var password : String?= "default",

        @field:SerializedName("Jenis_kelamin")
        var jenis_kelamin : String ?= "Laki-Laki",

        @field:SerializedName("alamat")
        var alamat : String ?= "Belum Ada Alamat",

        @field:SerializedName("tinggi")
        var tinggi : Int  ?= 0,

        @field:SerializedName("berat")
        var berat : Int ?= 0,

        @field:SerializedName("balance")
        var balance : Double =  1000.0
) : Serializable {
        var item : MutableList<ObatResponse> = mutableListOf()
}
/*
class User : Serializable{

    var email : String = "unknown"
    var id : Int=0
    var password : String ?= null
    var nama : String?=null
    var balance : Float = 0.0F
    var alamat : String?= null
    var tglLahir : String = "0/0/0000"
    var item : MutableList<Obat> = mutableListOf()
    var berat : Float = 45.0f
    var tinggi : Float = 0.0f
    constructor()
    constructor(email: String , password : String , nama_user : String, tglLahir : String) {
        this.nama =nama_user
        this.id=0
        this.password = password
        this.tglLahir = tglLahir
        this.email = email
        this.balance = 0.0F
    }
}
*/
