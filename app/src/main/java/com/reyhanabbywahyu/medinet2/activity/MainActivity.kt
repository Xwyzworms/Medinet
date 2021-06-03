package com.reyhanabbywahyu.medinet2.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.reyhanabbywahyu.medinet2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            //val intent = Intent(this,Daftar_Activity::class.java)

            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}