package com.reyhanabbywahyu.medinet2.`class`.getdata

import com.google.gson.annotations.SerializedName
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse


data class ResponseGetDataObat (
        @field:SerializedName("data")
        val data : MutableList<ObatResponse> ?= null,

        @field:SerializedName("isSuccess")
        val isSuccess : Boolean ?= null,

        @field:SerializedName("message")
        val message : String? = null
)