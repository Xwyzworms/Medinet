package com.reyhanabbywahyu.medinet2.ui.Biodata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BiodataViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Biodata Fragment"
    }
    val text: LiveData<String> = _text
}