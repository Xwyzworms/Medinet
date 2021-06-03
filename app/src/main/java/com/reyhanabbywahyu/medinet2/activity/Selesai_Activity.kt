package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.reyhanabbywahyu.medinet2.BerandaActivity
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.UserResponse

class Selesai_Activity : AppCompatActivity() {
    var user : UserResponse = UserResponse()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selesai_)
        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
        Handler().postDelayed({
            val intent = Intent(this,BerandaActivity::class.java)
            intent.putExtra("EXTRA_USER",user)
            startActivity(intent)
            finish()
        },3000)
    }
}