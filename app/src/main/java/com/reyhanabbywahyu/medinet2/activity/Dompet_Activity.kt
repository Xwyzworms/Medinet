package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import com.reyhanabbywahyu.medinet2.BerandaActivity
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.action.ResponseAction
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dompet_Activity : AppCompatActivity() {
    private lateinit var  tvBerandaSaldo : TextView
    private lateinit var  cvSaldo1 : CardView
    private lateinit var  cvSaldo2 : CardView
    private lateinit var  cvSaldo3 : CardView
    private lateinit var  cvSaldo4 : CardView
    private lateinit var  cvSaldo5 : CardView
    private lateinit var  cvSaldo6 : CardView

    private lateinit var  imgDompetPrev : ImageView
    private lateinit var etNominalDompet : EditText

    private  lateinit var  btnTopUpGas : Button
    private lateinit var  user : UserResponse
    var totalTotalTopUp : Double = 0.0
    private fun initViews() {

        tvBerandaSaldo = findViewById(R.id.tx_beranda_saldo)
        cvSaldo1 = findViewById(R.id.saldo1)
        cvSaldo2 = findViewById(R.id.saldo2)
        cvSaldo3 = findViewById(R.id.saldo3)
        cvSaldo4 = findViewById(R.id.saldo4)
        cvSaldo5 = findViewById(R.id.saldo5)
        cvSaldo6 = findViewById(R.id.saldo6)
        imgDompetPrev = findViewById(R.id.imgdompetPrev)

        etNominalDompet = findViewById(R.id.etnominalDompet)
        btnTopUpGas = findViewById(R.id.btnTopUpGas)
    }

    fun getUser() {
        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
    }

    fun setTheTopupView() {
        etNominalDompet.setText( totalTotalTopUp.toString())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dompet)
        initViews()
        getUser()

        tvBerandaSaldo.text = user.balance.toString()
        imgDompetPrev.setOnClickListener {
            val intent = Intent(applicationContext , BerandaActivity::class.java)
            intent.putExtra("EXTRA_USER",user)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        cvSaldo1.setOnClickListener {
            totalTotalTopUp += 25000.0
            setTheTopupView()
        }
        cvSaldo2.setOnClickListener {
            totalTotalTopUp += 50000.0
            setTheTopupView()
        }
        cvSaldo3.setOnClickListener {
            totalTotalTopUp += 100000.0
            setTheTopupView()
        }
        cvSaldo4.setOnClickListener {
            totalTotalTopUp += 200000.0
            setTheTopupView()
        }
        cvSaldo5.setOnClickListener {
            totalTotalTopUp += 250000.0
            setTheTopupView()
        }
        cvSaldo6.setOnClickListener {
            totalTotalTopUp += 300000.0
            setTheTopupView()
        }

        btnTopUpGas.setOnClickListener {
            Log.d("debug","Berhasil Topup sebesar ${etNominalDompet.text.toString()}")
            val total = etNominalDompet.text.toString().toDouble() + user.balance
            NetworkModule.service().updateBalance(user.id_user,total ).enqueue(object : Callback<ResponseAction> {
                override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {

                    Toast.makeText(applicationContext,"Berhasil Top up sebesar ${etNominalDompet.text.toString()}",Toast.LENGTH_LONG).show()
                    user.balance = total
                    tvBerandaSaldo.text = user.balance.toString()
                    totalTotalTopUp = 0.0
                    setTheTopupView()
                }

                override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed Top Up : ${t.message}",Toast.LENGTH_LONG).show()
                }
            })
        }


    }
}