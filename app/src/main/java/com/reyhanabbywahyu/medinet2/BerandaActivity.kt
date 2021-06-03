package com.reyhanabbywahyu.medinet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.reyhanabbywahyu.medinet2.Adapter.Imun_Beranda_Adapter
import com.reyhanabbywahyu.medinet2.Adapter.Rekomendasi_Beranda_Adapter
import com.reyhanabbywahyu.medinet2.Adapter.TokoObatAdapter
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
//import com.reyhanabbywahyu.medinet2.Adapter.Imun_Beranda_Adapter
//import com.reyhanabbywahyu.medinet2.Adapter.Rekomendasi_Beranda_Adapter
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.activity.Detail_ObatActivity
import com.reyhanabbywahyu.medinet2.activity.Dompet_Activity
import com.reyhanabbywahyu.medinet2.activity.Keranjang_Activity
import com.reyhanabbywahyu.medinet2.activity.Toko_Obat_Activity
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class BerandaActivity : AppCompatActivity() {
    var user: UserResponse = UserResponse()
    private lateinit var recyclerView: RecyclerView
    lateinit var btn_covid: CardView
    lateinit var btn_demam: CardView
    lateinit var btn_sakitkepala: CardView
    lateinit var btn_maag: CardView
    lateinit var btn_lainnya: CardView
    lateinit var btn_flu: CardView
    lateinit var tv_beranda_saldo : TextView
    lateinit var cd_beranda_topup : CardView
    lateinit var cd_beranda_bayar : CardView
    var dataObatImun : MutableList<ObatResponse>? = mutableListOf()
    var listObat : MutableList<ObatResponse>? =  mutableListOf()
    var i = 0
    private fun CheckItem(item : ObatResponse) {

        if(user.item.size != 0   ) {
            var ada : MutableList<String?> = mutableListOf()
            for (i in 0..(user.item.size - 1)) {
                ada.add(user.item[i].id)
                if (user.item[i].id == item.id && user.item[i].quantity == item.quantity) {
                    user.item[i].quantity = user.item[i].quantity?.plus(1)
                }
                else if (user.item[i].id == item.id && user.item[i].quantity != item.quantity) {
                    user.item[i].quantity = user.item[i].quantity?.plus(1)
                }
            }
            if(!ada.contains(item.id)) {
                user.item.add(item)
            }
        }
        else {
            Log.d("Tambah","Berhasil di Input")
            user.item.add(item)
        }
    }
    fun initViews() {

        btn_covid = findViewById(R.id.cvbtnBerandaCovid)
        btn_demam = findViewById(R.id.cvbtnBerandaDemam)
        btn_sakitkepala = findViewById(R.id.cvbtnBerandaSakitKepala)
        btn_maag = findViewById(R.id.cvbtnBerandaMaag)
        btn_flu = findViewById(R.id.cvbtnBerandFlu)
        btn_lainnya = findViewById(R.id.cvbtnBerandaLainnya)
        tv_beranda_saldo = findViewById(R.id.tx_beranda_saldo)
        cd_beranda_bayar = findViewById(R.id.crd_beranda_bayar)
        cd_beranda_topup = findViewById(R.id.crd_Beranda_topup)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)
        initViews()

        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
        fun gotoObatFragment(kategori:String) : Unit{

            var intent = Intent(this,KedaiObat_Activity::class.java)
            intent.putExtra("EXTRA_KATEGORI",kategori)
            intent.putExtra("EXTRA_USER",user )
            startActivity(intent)
        }

        tv_beranda_saldo.text = user.balance.toString()

        cd_beranda_topup.setOnClickListener {
            val intent = Intent(applicationContext,Dompet_Activity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("EXTRA_USER", user)
            startActivity(intent)
        }
       cd_beranda_bayar.setOnClickListener {
           val intent = Intent(applicationContext, Keranjang_Activity::class.java)
           intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
           intent.putExtra("EXTRA_USER",user)
           startActivity(intent)
       }
        btn_covid.setOnClickListener {
            gotoObatFragment("covid")
        }

        btn_sakitkepala.setOnClickListener {
            gotoObatFragment("sakitkepala")
        }

        btn_demam.setOnClickListener {
            gotoObatFragment("demam")
        }
        btn_maag.setOnClickListener {
            gotoObatFragment("maag")
        }
        btn_flu.setOnClickListener {
            gotoObatFragment("flu")
        }
        btn_lainnya.setOnClickListener {
            var intent = Intent(this, Toko_Obat_Activity::class.java)
            intent.putExtra("EXTRA_USER",user as java.io.Serializable)
            startActivity(intent)
        }

        //RecyclerView Rekomendasi Beranda
        val networkDataObat = NetworkModule.service().getAllObat()
        networkDataObat.enqueue(object : Callback<ResponseGetDataObat> {
            override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                listObat = response.body()?.data
                var linearManager_rekomendasi = LinearLayoutManager(applicationContext,
                        LinearLayoutManager.HORIZONTAL,false)
                recyclerView = findViewById(R.id.recycler_beranda_rekomendasi)
                recyclerView.setHasFixedSize(true)
                recyclerView.setAdapter( Rekomendasi_Beranda_Adapter(listObat,object : TokoObatAdapter.onClickListener {
                    override fun detail(item: ObatResponse) {
                        var intent = Intent(applicationContext, Detail_ObatActivity::class.java)
                        intent.putExtra("EXTRA_USER",user as Serializable)
                        intent.putExtra("EXTRA_OBAT",item )
                        startActivity(intent)
                    }

                    override fun tambah(item : ObatResponse) {
                        CheckItem(item)
                    }
                }))
                recyclerView.setLayoutManager(linearManager_rekomendasi)
            }

            override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed to Fetch Obat Rekomendasi",Toast.LENGTH_LONG).show()
            }
        })


        //RecyclerView Perkuat Imun Beranda
/*        val networkDataObat2 = NetworkModule.service().getAllObat()
        networkDataObat2.enqueue(object : Callback<ResponseGetDataObat>{
            override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                dataObatImun = response.body()?.data
                var linearManager_imun = LinearLayoutManager(applicationContext,
                        LinearLayoutManager.HORIZONTAL,false)
                recyclerView = findViewById(R.id.recycler_beranda_imun)
                recyclerView.setHasFixedSize(true)
                recyclerView.setAdapter( Imun_Beranda_Adapter(dataObatImun,object : TokoObatAdapter.onClickListener {
                    override fun detail(item: ObatResponse) {
                        var intent = Intent(applicationContext, Detail_ObatActivity::class.java)
                        intent.putExtra("EXTRA_USER",user as Serializable)
                        intent.putExtra("EXTRA_OBAT",item)
                        startActivity(intent)
                    }

                    override fun tambah(item:ObatResponse) {
                      CheckItem(item)

                    }
                }))
                recyclerView.setLayoutManager(linearManager_imun)

 */

                val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)
                bottomNavigation.selectedItemId = R.id.nav_beranda
                bottomNavigation.setOnNavigationItemSelectedListener { item ->
                    when(item.itemId){
                        R.id.nav_obat ->{
                            val intent_obat = Intent(applicationContext,KedaiObat_Activity::class.java)
                            intent_obat.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            intent_obat.putExtra("EXTRA_USER",user as Serializable)
                            startActivity(intent_obat)
                        }
                        R.id.nav_biodata ->{
                            val intent_biodata = Intent(applicationContext,BiodataActivity::class.java)
                            intent_biodata.putExtra("EXTRA_USER",user as Serializable)
                            intent_biodata.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            startActivity(intent_biodata)
                        }

                    }
                    true
                }

    }
}
