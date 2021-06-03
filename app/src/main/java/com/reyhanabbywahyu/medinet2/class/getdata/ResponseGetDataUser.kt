package com.reyhanabbywahyu.medinet2.`class`.getdata

import com.google.gson.annotations.SerializedName
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import java.io.Serializable

data class ResponseGetDataUser (

        @field:SerializedName("data")
        val data : List<UserResponse> ?= null,

        @field:SerializedName("isSuccess")
        val isSuccess : Boolean ?= null,

        @field:SerializedName("message")
        val message : String? = null

        ) : Serializable
