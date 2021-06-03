package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.reyhanabbywahyu.medinet2.DBHelper.DBHelper
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.action.ResponseAction
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataUser
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Daftar_Activity : AppCompatActivity() {
    lateinit var btnDaftar : Button
    lateinit var etDaftarEmail : EditText
    lateinit var etDaftarNama : EditText
    lateinit var etDaftarPassword : EditText
    lateinit var etDaftarPasswordKonfirmasi : EditText
    lateinit var etDaftarTanggalLahir : EditText
//    lateinit var database : DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)
        btnDaftar = findViewById(R.id.btnDaftar)
        etDaftarTanggalLahir = findViewById(R.id.etDaftarTanggalLahir)
        etDaftarPasswordKonfirmasi = findViewById(R.id.etDaftarPasswordKonfirmasi)
        etDaftarPassword = findViewById(R.id.etDaftarPassword)
        etDaftarNama = findViewById(R.id.etDaftarNama)
        etDaftarEmail = findViewById(R.id.etDaftarEmail)
//        database = DBHelper(this)
        btnDaftar.setOnClickListener {
            validateEmail()
            }
        }

    private fun validateEmailNow (email : String) : Boolean{
        return  email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validateDate(date : String) : Boolean {
        val regex =  Regex("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))\$")
        Toast.makeText(applicationContext,regex.find(date).toString(),Toast.LENGTH_SHORT).show()
        return regex.matches(date)
    }
    fun validateEmail() : Unit {
        var email: String = etDaftarEmail.text.toString()
        var tgl: String = etDaftarTanggalLahir.text.toString()
        if (validateEmailNow(email) && validateDate(tgl)) {


            NetworkModule.service().getUserByEmail(email).enqueue(object : Callback<ResponseGetDataUser> {
                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {

                    if (response.body()?.data?.size == 0) {
                        InsertData()
                        intent = Intent(applicationContext, Login_Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Email telah terdaftar", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }
        else if (!validateDate(tgl)) {
            Toast.makeText(applicationContext,"Format Date : YYYY-mm-dd",Toast.LENGTH_SHORT).show()
        }
        else if (!validateEmailNow(email)) {
            Toast.makeText(applicationContext,"Format Email Salah",Toast.LENGTH_SHORT).show()
        }
    }
    fun InsertData() : Unit {

        var namaLengkap : String = etDaftarNama.text.toString()
        var email : String = etDaftarEmail.text.toString()
        var password : String = etDaftarPassword.text.toString()
        var tglLahir : String = etDaftarTanggalLahir.text.toString()
        var konfirmasi : String = etDaftarPasswordKonfirmasi.text.toString()

        if(konfirmasi == password) {
            var networkUser : Call<ResponseAction> = NetworkModule.service().insertUser(namaLengkap,email,password,tglLahir)
            networkUser.enqueue(object : Callback<ResponseAction> {
                override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                    Log.d("ResponseInsert",response.toString())
                    Toast.makeText(applicationContext,"Berhasil Mendaftar",Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                    Toast.makeText(applicationContext,"Terjadi Kesalahan saat mendaftar",Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun Login(view: View) {
        val intent_login = Intent(this, Login_Activity::class.java)
        startActivity(intent_login)
        finish()
    }

}
