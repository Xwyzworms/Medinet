package com.reyhanabbywahyu.medinet2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse


class Demam_Obat_Adapter(var dataObatDemam : MutableList<ObatResponse>?, val onclick : TokoObatAdapter.onClickListener) : RecyclerView.Adapter<Demam_Obat_Adapter.HolderData>(), Filterable {
    var FilterList : MutableList<ObatResponse> ?= dataObatDemam
    class HolderData(val view : View) : RecyclerView.ViewHolder(view) {
        val nama_obat : TextView = itemView.findViewById(R.id.tvDetailJudulObat)
        val jumlah_jual : TextView = itemView.findViewById(R.id.tvDetailPenjelasanObat)
        val harga : TextView = itemView.findViewById(R.id.tvDetailHargaObat)
        var btn_tambah : Button = itemView.findViewById(R.id.btnDetailTambahObatRv)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_detail_obat, parent,false)
        return HolderData(view)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        var obat : ObatResponse = FilterList?.get(position)!!
        holder.nama_obat.text = obat.nama
        holder.jumlah_jual.text = obat.jumlahdijual
        holder.harga.text = obat.harga.toString()
        holder.view.setOnClickListener {
            onclick.detail(obat)
        }
        holder.btn_tambah.setOnClickListener {
            onclick.tambah(obat)
        }
    }

    override fun getItemCount(): Int {
        return FilterList?.size!!
    }

    override fun getFilter(): Filter {
       return object : Filter () {
           override fun performFiltering(constraint: CharSequence?): FilterResults {
              val charsearch = constraint.toString()
              if(charsearch.isEmpty())  {
                  FilterList = dataObatDemam
              }
               else {
                   val result : MutableList<ObatResponse> = mutableListOf()
                  for (dat in dataObatDemam!!) {
                      if (dat.nama?.toLowerCase()?.contains(charsearch.toLowerCase()) == true) {
                          result.add(dat)
                      }
                  }
                  FilterList = result
               }
               val filterResult = FilterResults()
               filterResult.values =  FilterList
               return filterResult
           }

           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               FilterList = results?.values as MutableList<ObatResponse>
               notifyDataSetChanged()
           }

       }
    }
}