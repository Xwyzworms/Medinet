package com.pritim.myapplication.ui.Obat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pritim.myapplication.R

class ObatFragment : Fragment() {

    private lateinit var obatViewModel: ObatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        obatViewModel =
            ViewModelProvider(this).get(ObatViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_obat, container, false)
        return root
    }
}