package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.User
import kotlinx.android.synthetic.main.activity_login.*

class Login_Activity : AppCompatActivity() {
    lateinit  var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("tikuk","78yiuhj")
        btnMasuk.setOnClickListener {
            Log.d("tikuk","jkuh")
            Toast.makeText(this,"TOLOL",Toast.LENGTH_LONG).show()
            var etMasukEmailtext: String = etMasukEmail.text.toString()
            var etMasukPassword: String = etMasukPassword.text.toString()
            user = User(etMasukEmailtext, etMasukPassword, "Roy")

            if (validate_login(user)) {
                Toast.makeText(this,"Login Berhasi",Toast.LENGTH_LONG).show()
                loginSuccesfull()
            }
            else {
                Toast.makeText(this, " Login gagal TOLOL",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun validate_login(User : User) : Boolean {

        if(User.email == "kacang" && User.password == "123456") {
            return true
        }
        return false
    }

    fun loginSuccesfull() {
        intent = Intent(this,UtamaActivity::class.java)
        intent.putExtra("EXTRA_USER",user)
        startActivity(intent)
    }
}