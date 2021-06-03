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


class Maag_Obat_Adapter(var dataObatMaag : MutableList<ObatResponse>?,val onclick : TokoObatAdapter.onClickListener) : RecyclerView.Adapter<Maag_Obat_Adapter.HolderData>(), Filterable {
    var FilterList = dataObatMaag
    class HolderData(val view : View) : RecyclerView.ViewHolder(view) {

        val nama_obat : TextView = itemView.findViewById(R.id.tvDetailJudulObat)
        val jumlah_jual : TextView = itemView.findViewById(R.id.tvDetailPenjelasanObat)
        val harga : TextView = itemView.findViewById(R.id.tvDetailHargaObat)
        val btn_tambah : Button = itemView.findViewById(R.id.btnDetailTambahObatRv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_detail_obat, parent,false)
        return HolderData(view)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val item : ObatResponse = FilterList?.get(position) !!
        holder.nama_obat.text = item.nama
        holder.jumlah_jual.text = item.jumlahdijual
        holder.harga.text = item.harga.toString()
        holder.view.setOnClickListener {
            onclick.detail(item)
        }
        holder.btn_tambah.setOnClickListener {
            onclick.tambah(item)
        }
    }

    override fun getItemCount(): Int {
        return FilterList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchChar = constraint.toString()
                if(searchChar.isEmpty()) {
                    FilterList = dataObatMaag
                }
                else {
                    val result : MutableList<ObatResponse> = mutableListOf()
                    for ( dat in dataObatMaag!!) {
                        if(dat.nama?.toLowerCase()?.contains(searchChar.toLowerCase()) == true) {
                            result.add(dat)
                        }
                    }
                    FilterList = result
                }
                val resultList =  FilterResults()
                resultList.values = FilterList
                return resultList
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                FilterList = results?.values as MutableList<ObatResponse>
                notifyDataSetChanged()
            }

        }
    }
}
