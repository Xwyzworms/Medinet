package com.reyhanabbywahyu.medinet2.config

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkImage {
    fun getRetrofitImage() : Retrofit {

        return Retrofit.Builder().baseUrl("http://10.0.2.2/BackendApi/").
                addConverterFactory(ScalarsConverterFactory.create()).build()
    }
    fun service() : IOService = NetworkModule.getRetrofit().create(IOService::class.java)
}