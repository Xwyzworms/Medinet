package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import com.reyhanabbywahyu.medinet2.KedaiObat_Activity
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import java.io.Serializable

class Detail_ObatActivity : AppCompatActivity() {
    lateinit var tvDetailJudul : TextView
    lateinit var tvDetailKeterangan : TextView
    lateinit var tvDetailHarga : TextView
    lateinit var btnKeranjangKurang : Button
    lateinit var  etKeranjangTotalObat : EditText
    lateinit var btnKeranjangTambah : Button

    lateinit var tvDetailInformasiUmum : TextView
    lateinit var tvDetailPeringatan : TextView
    lateinit var tvDetailDetail : TextView

    lateinit var  tvDetailHargaAkhir : TextView
    lateinit var  imgDetailPrev : ImageView
    lateinit var btnDetailTambah : TextView

    lateinit var  cvDetailObatBeliSekarang : CardView
     var User : UserResponse = UserResponse()
     var OBat : ObatResponse = ObatResponse()
     var total : Int? =1
    override fun onBackPressed() {
        //super.onBackPressed()
        //moveTaskToBack(true);

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_obat)

        tvDetailJudul = findViewById(R.id.tvDetailJudul)
        tvDetailKeterangan = findViewById(R.id.tvDetailKeterangan)
        tvDetailHarga = findViewById(R.id.tvDetailHarga)
        btnKeranjangKurang = findViewById(R.id.btnKeranjangKurang)
        btnKeranjangTambah = findViewById(R.id.btnKeranjangTambah)
        etKeranjangTotalObat = findViewById(R.id.etKeranjangTotalObat)
        tvDetailPeringatan = findViewById(R.id.tvDetailPeringatan)
        tvDetailDetail = findViewById(R.id.tvDetailDetail)
        tvDetailHargaAkhir = findViewById(R.id.tvDetailHargaAkhir)
        btnDetailTambah = findViewById(R.id.btnDetailTambah)
        tvDetailInformasiUmum = findViewById(R.id.tvDetailInformasiUmum)
        imgDetailPrev = findViewById(R.id.imgDetailPrev)
        cvDetailObatBeliSekarang = findViewById(R.id.cvDetailObatBeliSekarang)
        User = intent.getSerializableExtra("EXTRA_USER") as UserResponse
        var Obat : ObatResponse = intent.getSerializableExtra("EXTRA_OBAT") as ObatResponse

        tvDetailJudul.text = Obat.nama
        tvDetailKeterangan.text = Obat.jumlahdijual
        tvDetailHarga.text = Obat.harga.toString()
        tvDetailInformasiUmum.text = Obat.informasiumum

        if (User.item.size != 0) {
            for (i in 0.. (User.item.size -1 ))
            {
                if(User.item[i].id == Obat.id) {
                    etKeranjangTotalObat.setText(User.item[i].quantity.toString())
                    total = User.item[i].quantity
                }
            }
        }
        else {
            etKeranjangTotalObat.setText(total.toString())
        }


        tvDetailHargaAkhir.text = (tvDetailHarga.text.toString().toDouble() * total!!).toString()
        btnKeranjangKurang.setOnClickListener {
            etKeranjangTotalObat.setText(kurang().toString())
        }
        btnKeranjangTambah.setOnClickListener {
            etKeranjangTotalObat.setText(tambah().toString())
        }
        imgDetailPrev.setOnClickListener {
         //   intent = Intent(applicationContext, Toko_Obat_Activity::class.java)
            intent = Intent(applicationContext, KedaiObat_Activity::class.java)
            intent.putExtra("EXTRA_USER",User)
            startActivity(intent)
        }
        cvDetailObatBeliSekarang.setOnClickListener {
            var containTheObat = false
            var listIdObat : MutableList<String> = mutableListOf()
            Toast.makeText(this,"Obat Berhasil Ditambahkan",Toast.LENGTH_LONG).show()
            if(User.item.size != 0   ) {
                for (i in 0..(User.item.size -1 )) {
                    if (User.item[i].id == Obat.id && User.item[i].quantity != total) {
                        User.item[i].quantity = total
                    }
                    listIdObat?.add(User.item[i].id!!)
                }
                if (listIdObat != null) {
                    if (Obat.id!! !in listIdObat) {
                        Obat.quantity =etKeranjangTotalObat.text.toString().toInt()
                        Log.d("debugObat",Obat.id.toString())
                        User.item.add(Obat)
                    }
                }

            }
            else {
                Obat.quantity =etKeranjangTotalObat.text.toString().toInt()
                User.item.add(Obat)
                Log.d("debug",User.item.size.toString())

            }
            intent = Intent(applicationContext,Keranjang_Activity::class.java )
            intent.putExtra("EXTRA_USER",User as Serializable)
            intent.putExtra("EXTRA_OBAT",Obat as Serializable)
            startActivity(intent)
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.isNotBlank() || s.isNotEmpty()) {
                        total = etKeranjangTotalObat.text.toString().toInt()
                        hitungHargaTotal()
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        etKeranjangTotalObat.addTextChangedListener(textWatcher)
    }


    fun tambah() : Int?{
        total= total?.plus(1)
        hitungHargaTotal()
        return total
    }
    fun hitungHargaTotal() : Unit{

        var hargatot = total?.times(tvDetailHarga.text.toString().toDouble())
        tvDetailHargaAkhir.text = hargatot.toString()

    }
    fun kurang() : Int? {
        if (total!! <1) {
            total = 0
            tvDetailHargaAkhir.text = "0"
        }
        else {
            total = total!! - 1
            hitungHargaTotal()
        }
        return total
    }
}