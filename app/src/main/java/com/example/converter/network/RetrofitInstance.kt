package com.example.converter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val api : CbInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CbInterface::class.java)
    }
}