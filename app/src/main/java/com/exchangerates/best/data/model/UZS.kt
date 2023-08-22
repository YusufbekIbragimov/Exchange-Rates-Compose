package com.exchangerates.best.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UZS(
    @Json(name = "code")
    val code: String,
    @Json(name = "value")
    val value: Double
)