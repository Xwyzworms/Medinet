package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.reyhanabbywahyu.medinet2.BerandaActivity
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataUser
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import com.reyhanabbywahyu.medinet2.utils.PreferenceUtils
import com.reyhanabbywahyu.medinet2.utils.user
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class Login_Activity : AppCompatActivity() {
    lateinit var btnMasuk: Button
    lateinit var etMasukEmail: EditText
    lateinit var etMasukPassword: EditText
   // lateinit var database: DBHelper
    private fun checkIfAccountExists() {

        if (PreferenceUtils.getEmail(this) != null && PreferenceUtils.getPassword(this) != null ) {
            var intent : Intent = Intent(this@Login_Activity,BerandaActivity::class.java)
            NetworkModule.service().getUserByEmail(PreferenceUtils.getEmail(applicationContext) !!).enqueue(object : Callback<ResponseGetDataUser> {
                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                   user = response.body()?.data?.get(0) !!
                    if (user != null) {
                        intent.putExtra("EXTRA_USER", user)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext,"Belum terdaftar",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    Toast.makeText(applicationContext,"No Email or Password saved",Toast.LENGTH_LONG).show()
                }
            })
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       // database = DBHelper(thiso)
        btnMasuk = findViewById(R.id.btnMasuk)
        etMasukPassword = findViewById(R.id.etMasukPassword)
        etMasukEmail = findViewById(R.id.etMasukEmail)

        checkIfAccountExists()
        btnMasuk.setOnClickListener {
            var email : String = etMasukEmail.text.toString()
            val userNetwork  : Call<ResponseGetDataUser> = NetworkModule.service().getUserByEmail(email)
            userNetwork.enqueue(object : Callback<ResponseGetDataUser> {
                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                    var usergetDatabase = response.body()?.data?.get(0)
                    if (usergetDatabase == null) {
                        Toast.makeText(applicationContext, "Email atau Password Salah !!", Toast.LENGTH_LONG).show()
                    } else {
                        var etMasukPassword: String = etMasukPassword.text.toString()
                        if (etMasukPassword == usergetDatabase!!.password.toString()) {
                            Toast.makeText(applicationContext, "Berhasil Masuk", Toast.LENGTH_LONG).show()
                            PreferenceUtils.saveEmail(usergetDatabase.email!!,applicationContext)
                            PreferenceUtils.savePassword(usergetDatabase.password!!,applicationContext)
                            emptyInputEditText()
                            intent = Intent(applicationContext, BerandaActivity::class.java)
                            intent.putExtra("EXTRA_USER", usergetDatabase as Serializable)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Password Salah", Toast.LENGTH_LONG).show()
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                }
            })

        }
    }

    fun Daftar(view: View) {
        val intent_Registration = Intent(this, Daftar_Activity::class.java)
        startActivity(intent_Registration)
        finish()
    }

    private fun emptyInputEditText() {
        etMasukEmail.setText(null)
        etMasukPassword.setText(null)
    }



}