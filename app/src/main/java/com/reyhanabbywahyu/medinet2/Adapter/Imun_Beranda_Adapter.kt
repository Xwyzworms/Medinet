package com.reyhanabbywahyu.medinet2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse


class Imun_Beranda_Adapter(var ListObatimun : List<ObatResponse>? ,val onclick : TokoObatAdapter.onClickListener) : RecyclerView.Adapter<Imun_Beranda_Adapter.HolderData>() {
    class HolderData(val view : View) : RecyclerView.ViewHolder(view) {
        val nama_obat : TextView = itemView.findViewById(R.id.nama_obat)
        val jumlah_jual : TextView = itemView.findViewById(R.id.jumlah_jual)
        val harga : TextView = itemView.findViewById(R.id.harga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_obat_beranda, parent,false)
        return HolderData(view)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val obat : ObatResponse = ListObatimun?.get(position) !!
        holder.nama_obat.text = obat.nama
        holder.jumlah_jual.text = obat.jumlahdijual
        holder.harga.text = obat.harga.toString()

        holder.view.setOnClickListener {
           onclick.detail(obat)
        }
    }

    override fun getItemCount(): Int {
        return ListObatimun?.size!!
    }
}
