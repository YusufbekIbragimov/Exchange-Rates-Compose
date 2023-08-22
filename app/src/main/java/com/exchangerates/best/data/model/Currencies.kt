package com.exchangerates.best.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currencies(
    @Json(name = "UZS")
    val uZS: UZS
)