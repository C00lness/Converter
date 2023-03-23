package com.example.converter.network

import android.util.Log
import android.widget.Toast
import com.example.converter.BuildConfig
import com.example.converter.model.CBApiResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.io.IOException
import com.example.converter.model.CBRates
import com.squareup.moshi.Json
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class CBService {
    //private val okHttp = OkHttpClient()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val service = retrofit.create(CBServiceInterface::class.java)

    fun request(callback: (response: String?) -> Unit) {
        val call = service.getRates()
        call.enqueue(object :Callback<CBApiResponse>{
            override fun onResponse(call: Call<CBApiResponse>, response: Response<CBApiResponse>) {
                callback.invoke(response.body().toString())
            }

            override fun onFailure(call: Call<CBApiResponse>, t: Throwable) {
                Log.d("Error", t.toString())
            }

        })
    }
    interface CBServiceInterface {
        @GET("daily_json.js")
        fun getRates(): Call<CBApiResponse>
    }

}
