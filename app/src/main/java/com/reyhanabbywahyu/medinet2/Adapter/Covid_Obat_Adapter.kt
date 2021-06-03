package com.reyhanabbywahyu.medinet2.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataObat
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Covid_Obat_Adapter(val dataObats : MutableList<ObatResponse>? ,val onclick : TokoObatAdapter.onClickListener ) : RecyclerView.Adapter<Covid_Obat_Adapter.HolderData>(),Filterable {
    var FilterList: MutableList<ObatResponse>? = dataObats
    init {
        FilterList = dataObats
    }
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
        val obat : ObatResponse = FilterList?.get(position)!!
        holder.nama_obat.text =  obat.nama
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
    override fun getFilter() : Filter {
        return object : Filter () {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charsearch = constraint.toString()
                if(charsearch.isEmpty()) {
                        FilterList = dataObats
                }
                else {
                    val resultList : MutableList<ObatResponse> = mutableListOf()
                    for (dat in dataObats!!) {
                        if(dat.nama?.toLowerCase()?.contains(charsearch.toLowerCase()) == true) {
                            resultList.add(dat)
                        }
                    }
                   FilterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = FilterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                FilterList = results?.values as MutableList<ObatResponse>
                Log.d("FILTERED",FilterList?.size.toString())
                notifyDataSetChanged()
            }

        }
    }
}