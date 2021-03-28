package com.reyhanabbywahyu.medinet2.ui.Beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BerandaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Beranda Fragment"
    }
    val text: LiveData<String> = _text
}