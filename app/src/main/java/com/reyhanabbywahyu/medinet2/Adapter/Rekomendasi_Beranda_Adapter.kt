package com.reyhanabbywahyu.medinet2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Callback

class Rekomendasi_Beranda_Adapter(val ListObat : List<ObatResponse>? ,val onclick : TokoObatAdapter.onClickListener) : RecyclerView.Adapter<Rekomendasi_Beranda_Adapter.HolderData>() {
    class HolderData(val view : View) : RecyclerView.ViewHolder(view){
        val nama_obat : TextView = itemView.findViewById(R.id.nama_obat)
        val jumlah_jual : TextView = itemView.findViewById(R.id.jumlah_jual)
        val harga : TextView = itemView.findViewById(R.id.harga)
        val btnTambah : Button = itemView.findViewById(R.id.btn_tambah)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_obat_beranda, parent,false)
        return HolderData(view)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val obat : ObatResponse = ListObat?.get(position)!!
        holder.nama_obat.text = obat.nama
        holder.jumlah_jual.text = obat.jumlahdijual
        holder.harga.text = obat.harga.toString()
        holder.btnTambah.setOnClickListener {
            onclick.tambah(obat)
        }
        holder.view.setOnClickListener {
            onclick.detail(obat )
        }

    }

    override fun getItemCount(): Int {
        return ListObat?.size!!
    }
}


