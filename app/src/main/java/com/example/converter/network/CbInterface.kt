package com.example.converter.network

import com.example.converter.model.CBApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface CbInterface {
    //@GET("daily/rub.json")
    //@GET("XML_daily.asp?date_req=22/03/2022")
    //@GET("/users")
    @GET("daily_json.js")
    suspend fun getUserData() : Response<CBApiResponse>
}