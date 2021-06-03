package com.reyhanabbywahyu.medinet2.config

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {
    var Gson = GsonBuilder().setLenient().create()
    fun getRetrofit() : Retrofit {

        return Retrofit.Builder().baseUrl("http://10.0.2.2/BackendApi/").addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson)).build()
    }

    fun service() : ApiService = getRetrofit().create(ApiService::class.java)

}