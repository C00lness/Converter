package com.example.converter.model

import com.squareup.moshi.Json

data class CBApiResponse(
    @field:Json(name = "Date") val date: String,
    @field:Json(name = "PreviousDate") val pdate: String,
    @field:Json(name = "PreviousURL") val purl: String,
    @field:Json(name = "Timestamp") val timeStamp: String,
    @field:Json(name = "Valute") val valute: Map<String, CBRates>
)

