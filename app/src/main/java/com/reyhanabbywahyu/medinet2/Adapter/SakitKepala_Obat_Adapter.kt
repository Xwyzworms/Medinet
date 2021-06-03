package com.reyhanabbywahyu.medinet2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse


class SakitKepala_Obat_Adapter(var obatSakitKepala : MutableList<ObatResponse>?,val onclick : TokoObatAdapter.onClickListener) : RecyclerView.Adapter<SakitKepala_Obat_Adapter.HolderData>(), Filterable {

    var FilterList : MutableList<ObatResponse>? = obatSakitKepala
    class HolderData(val view : View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val nama_obat : TextView = itemView.findViewById(R.id.tvDetailJudulObat)
        val jumlah_jual : TextView = itemView.findViewById(R.id.tvDetailPenjelasanObat)
        val harga : TextView = itemView.findViewById(R.id.tvDetailHargaObat)
        val btn_tambah : Button = itemView.findViewById(R.id.btnDetailTambahObatRv)

        override fun onClick(v: View?) {
            Toast.makeText(itemView.context, "", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_detail_obat, parent,false)
        return HolderData(view)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val item : ObatResponse = FilterList?.get(position)!!
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
                    FilterList = obatSakitKepala
                }
                else {
                    val result : MutableList<ObatResponse> = mutableListOf()
                    for ( dat in obatSakitKepala!!) {
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