package com.reyhanabbywahyu.medinet2

import android.content.Intent
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.reyhanabbywahyu.medinet2.Adapter.*
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.activity.Detail_ObatActivity
import com.reyhanabbywahyu.medinet2.activity.Toko_Obat_Activity
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
class KedaiObat_Activity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var obat_Search: androidx.appcompat.widget.SearchView
    lateinit var textViewJudulObat: TextView
    private var user: UserResponse = UserResponse()
    var listObat : MutableList<ObatResponse>? = mutableListOf()

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
    private fun getUser() {
        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kedai_obat)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        recyclerView = findViewById(R.id.recycler_obat_container)

        getUser()
        var kategori = intent.getStringExtra("EXTRA_KATEGORI")
        textViewJudulObat = findViewById(R.id.Obat_kategori_ganti)
        fun gotoDetail(item : ObatResponse) {

            var intent = Intent(applicationContext, Detail_ObatActivity::class.java)
            intent.putExtra("EXTRA_USER",user as Serializable)
            intent.putExtra("EXTRA_OBAT",item )
            startActivity(intent)
        }
        fun makeCovid(kategori : String) {
            val covidNetwork = NetworkModule.service().getObatByKategori(kategori)
            covidNetwork.enqueue(object : Callback<ResponseGetDataObat> {
                override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                    listObat = response.body()?.data
                    textViewJudulObat.text = "Covid-19"
                    var adapter = Covid_Obat_Adapter(listObat,object : TokoObatAdapter.onClickListener {
                        override fun detail(item: ObatResponse) {
                            gotoDetail(item)
                        }

                        override fun tambah(item: ObatResponse) {
                           CheckItem(item)
                        }
                    })
                    var linearManager_covid = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    obat_Search = findViewById(R.id.Obat_search)
                    recyclerView = findViewById(R.id.recycler_obat_container)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = linearManager_covid
                    recyclerView.setHasFixedSize(true)

                    obat_Search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }
                        override fun onQueryTextChange(newText: String?): Boolean {
                            adapter.filter.filter(newText)

                            return false
                        }
                    })
                }

                override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {
                }
            })

        }
        fun makeDemam(kategori : String): Unit {
            val demamNet = NetworkModule.service().getObatByKategori(kategori)
            demamNet.enqueue(object : Callback<ResponseGetDataObat>{
                override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                    listObat= response.body()?.data
                    textViewJudulObat.text = "Demam"
                    var adapter = Demam_Obat_Adapter(listObat,object : TokoObatAdapter.onClickListener{
                        override fun detail(item: ObatResponse) {
                            gotoDetail(item)
                        }

                        override fun tambah(item: ObatResponse) {
                            CheckItem(item)

                        }
                    })
                    obat_Search = findViewById(R.id.Obat_search)
                    recyclerView = findViewById(R.id.recycler_obat_container)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = adapter
                    recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
                    obat_Search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            adapter.filter.filter(newText)

                            return false
                        }
                    })
                }

                override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {
                }
            })

        }

        fun makeFlu(kategori: String): Unit {
            textViewJudulObat.text = "Flu"
            val flutNetwork = NetworkModule.service().getObatByKategori(kategori)
            flutNetwork.enqueue(object: Callback<ResponseGetDataObat>{
                override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                    var listObat = response.body()?.data
                    var adapter = Flu_Obat_Adapter(listObat,object  : TokoObatAdapter.onClickListener {
                        override fun detail(item: ObatResponse) {
                            gotoDetail(item)
                        }

                        override fun tambah(item: ObatResponse) {
                           CheckItem(item)
                        }
                    })
                    obat_Search = findViewById(R.id.Obat_search)
                    recyclerView = findViewById(R.id.recycler_obat_container)
                    recyclerView.adapter = adapter
                    recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))

                    obat_Search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            adapter.filter.filter(newText)
                            return false
                        }
                    } )
                }

                override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {
                    Log.d("WJ",t.message.toString())
                }

            })

        }

        fun makeMaag(kategori : String) {
            val maagNetwork = NetworkModule.service().getObatByKategori(kategori)
            maagNetwork.enqueue(object : Callback<ResponseGetDataObat>{
                override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                    var listObat = response.body()?.data
                    textViewJudulObat.text = "Maag"
                    val adapter = Maag_Obat_Adapter(listObat,object : TokoObatAdapter.onClickListener {
                        override fun detail(item: ObatResponse) {
                            gotoDetail(item)
                        }

                        override fun tambah(item: ObatResponse) {
                          CheckItem(item)
                        }
                    } )
                    obat_Search = findViewById(R.id.Obat_search)
                    recyclerView = findViewById(R.id.recycler_obat_container)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = adapter
                    recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))

                    obat_Search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            adapter.filter.filter(newText)
                            return false
                        }
                    } )

                }

                override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {
                }
            })

        }

        fun makeSakitPala(kategori:String) {

         var sakitPalaNetwork = NetworkModule.service().getObatByKategori(kategori)
            sakitPalaNetwork.enqueue(object : Callback<ResponseGetDataObat>{
                override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                    var listObat = response.body()?.data
                    textViewJudulObat.text = "Sakit Kepala"
                    val adapter = SakitKepala_Obat_Adapter(listObat ,object : TokoObatAdapter.onClickListener {
                        override fun detail(item: ObatResponse) {
                            gotoDetail(item)
                        }

                        override fun tambah(item: ObatResponse) {
                            CheckItem(item)
                        }
                    })
                    obat_Search = findViewById(R.id.Obat_search)
                    recyclerView = findViewById(R.id.recycler_obat_container)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = adapter

                    recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
                    obat_Search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            adapter.filter.filter(newText)
                            return false
                        }
                    } )
                }

                override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {

                }
            })

        }

        val btn_Covid: CardView = findViewById(R.id.btn_Covid)
        val btn_Demam: CardView = findViewById(R.id.btn_Demam)
        val btn_SakitKepala: CardView = findViewById(R.id.btn_SakitKepala)
        val btn_Flu: CardView = findViewById(R.id.btn_Flu)
        val btn_Maag: CardView = findViewById(R.id.btn_Maag)
        val btn_lainnya : CardView = findViewById(R.id.btn_lainnya)
        makeCovid("covid")
        when {
            kategori == "covid" -> {
                makeCovid(kategori)
            }

            kategori == "demam" -> {
                //Demam
                makeDemam(kategori)
            }
            kategori == "flu" -> {
                //Flu
                makeFlu(kategori)
            }
            kategori == "maag" -> {
                //Maag
                makeMaag(kategori)
            }
            kategori == "sakitkepala" -> {
                //Sakit Kepala
                makeSakitPala(kategori)
            }
        }

        btn_Covid.setOnClickListener {
            makeCovid("covid")
        }
        btn_Demam.setOnClickListener {
            makeDemam("demam")
        }
        btn_Flu.setOnClickListener {
            makeFlu("flu")
        }
        btn_Maag.setOnClickListener {
            makeMaag("maag")
        }
        btn_SakitKepala.setOnClickListener {
            makeSakitPala("sakitkepala")
        }
        btn_lainnya.setOnClickListener {

            var intent = Intent(applicationContext, Toko_Obat_Activity::class.java )
            intent.putExtra("EXTRA_USER",user)
            startActivity(intent)
        }
        bottomNavigation.selectedItemId = R.id.nav_obat
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_beranda ->{
                    val intent_beranda = Intent(this,BerandaActivity::class.java)
                    intent_beranda.putExtra("EXTRA_USER",user as Serializable)
                    intent_beranda.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent_beranda)
                }
                R.id.nav_biodata ->{
                    val intent_biodata = Intent(this,BiodataActivity::class.java)
                    intent_biodata.putExtra("EXTRA_USER",user as Serializable)
                    intent_biodata.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent_biodata)
                }
            }
            false
        }
    }
}
