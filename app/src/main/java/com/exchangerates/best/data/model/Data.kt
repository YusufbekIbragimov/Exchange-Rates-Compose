package com.exchangerates.best.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "currencies")
    val currencies: Currencies,
    @Json(name = "datetime")
    val datetime: String
)