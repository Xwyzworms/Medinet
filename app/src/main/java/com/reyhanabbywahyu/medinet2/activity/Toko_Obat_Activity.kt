package com.reyhanabbywahyu.medinet2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.Adapter.TokoObatAdapter
import com.reyhanabbywahyu.medinet2.KedaiObat_Activity
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Toko_Obat_Activity : AppCompatActivity() {
    lateinit var tokoObatSearch: androidx.appcompat.widget.SearchView
    lateinit var recyclerViewObat: RecyclerView
    var user: UserResponse? = null

    private fun getUser() {
        if(intent.getSerializableExtra("EXTRA_USER") != null ) {
            user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toko__obat)
        getUser()
        NetworkModule.service().getAllObat().enqueue(object : Callback<ResponseGetDataObat> {
            override fun onResponse(call: Call<ResponseGetDataObat>, response: Response<ResponseGetDataObat>) {
                var dataObat : List<ObatResponse> = response.body()?.data!!
                recyclerViewObat = findViewById(R.id.recyclerViewDetailObat)

                val btn_back : CardView = findViewById(R.id.btn_back)
                btn_back.setOnClickListener{
                    intent = Intent(applicationContext, KedaiObat_Activity::class.java)
                    intent.putExtra("EXTRA_USER" , user as java.io.Serializable)
                    startActivity(intent)
                    finish()
                }

                val adapter = TokoObatAdapter(dataObat,object : TokoObatAdapter.onClickListener {
                    override fun detail(item: ObatResponse) {
                        intent  = Intent(applicationContext,Detail_ObatActivity::class.java)
                        intent.putExtra("EXTRA_OBAT",item)
                        intent.putExtra("EXTRA_USER",user as java.io.Serializable)
                        startActivity(intent)
                        finish()
                    }

                    override fun tambah(item:ObatResponse) {

                    }
                }, user!!)
                recyclerViewObat.adapter = adapter
                tokoObatSearch = findViewById(R.id.tokoObat_search)
                tokoObatSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapter.filter.filter(newText)

                        return false
                    }
                }
                )

                recyclerViewObat.scrollToPosition(recyclerViewObat.adapter?.itemCount!! - 1)
            }

            override fun onFailure(call: Call<ResponseGetDataObat>, t: Throwable) {

            }
        })

    }
}
