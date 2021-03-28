package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.User
import kotlinx.android.synthetic.main.activity_daftar.*

class Daftar_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)
        btnDaftar.setOnClickListener {
            var user: User? = getData()
            if (user != null) {
                Log.d("HaloHaloBandung", user.email.toString(),)

                intent = Intent(this, Login_Activity::class.java)
                startActivity(intent)
            }
        }
    }
    fun getData() : User? {

        var namaLengkap : String = etDaftarNama.text.toString()
        var email : String = etDaftarEmail.text.toString()
        var password : String = etDaftarPassword.text.toString()
        var tglLahir : String = etDaftarTanggalLahir.text.toString()
        var konfirmasi : String = etDaftarPasswordKonfirmasi.text.toString()
        if(konfirmasi == password) {
            var user : User = User(email,password,namaLengkap,tglLahir)
            Toast.makeText(this,"Daftar Berhasil",Toast.LENGTH_LONG).show()
            return user
        }
        Toast.makeText(this,"Tah Tolol",Toast.LENGTH_LONG).show()
        return null

    }

}
