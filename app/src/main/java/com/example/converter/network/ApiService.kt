package com.example.converter.network

import com.example.converter.helper.EndPoints
import com.example.converter.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(EndPoints.CONVERT_URL)
    suspend fun convertCurrency(
        @Query("api_key") access_key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ) : Response<ApiResponse>
}