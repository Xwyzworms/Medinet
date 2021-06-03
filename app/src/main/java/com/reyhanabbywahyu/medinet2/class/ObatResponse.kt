package com.reyhanabbywahyu.medinet2.`class`

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObatResponse (

        @field:SerializedName("id_obat")
        val id : String ?= null,

        @field:SerializedName("nama")
        val nama : String ?= null,

        @field:SerializedName("kategori")
        val kategori : String ?= null,

        @field:SerializedName("harga_obat")
        val harga : Float ?= 0.0f ,

        @field:SerializedName("quantity")
        var quantity : Int ?= 1,

        @field:SerializedName("jumlah_dijual")
        val jumlahdijual : String ?= null,

        @field:SerializedName("informasi_umum")
        val informasiumum : String ?= "Belum ada Informasi terkait Obat ini",

        @field:SerializedName("informasi_detail")
        val informasiDetail : String ?= "Belum ada informasi terkait obat ini",

        @field:SerializedName("dosis")
        val dosis : String ?= "Dosis belum di informasikan"
        ) : Serializable

    /*
    constructor()
    constructor(id: Int, nama: String, harga: Double, kategori: String, infoPendek: String, infoDetail: String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.kategori = kategori
        this.jumlahdijual = infoPendek
        this.informasiumum = infoDetail
    }
    constructor (id : Int, nama : String, harga : Double, kategori : String, jumlahdijual : String, informasiumum : String, informasidetail: String, peringatan : String, dosis : String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.kategori = kategori
        this.jumlahdijual = jumlahdijual
        this.informasiumum = informasiumum
        this.quantity = 1
        this.informasiDetail = informasidetail
        this.Peringatan = peringatan
        this.Dosis = dosis
    }

    var id : Int =0
    var nama : String? =null
    var harga : Double=0.0
    var kategori : String?="umum"
    var jumlahdijual : String = "Belum Diketahui"
    var informasiumum : String = "Sebuah text yang sangat panjang"
    var quantity : Int = 1
    var informasiDetail : String = "masih kosng"
    var Peringatan : String = "Kosong"
    var Dosis : String = "500 kaplet per hari"

}
*/
