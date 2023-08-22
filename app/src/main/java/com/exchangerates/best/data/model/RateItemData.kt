package com.exchangerates.best.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateItemData(
    @Json(name = "Ccy")
    val ccy: String? = null,
    @Json(name = "CcyNm_EN")
    val ccyNmEN: String? = null,
    @Json(name = "CcyNm_RU")
    val ccyNmRU: String? = null,
    @Json(name = "CcyNm_UZ")
    val ccyNmUZ: String? = null,
    @Json(name = "CcyNm_UZC")
    val ccyNmUZC: String? = null,
    @Json(name = "Code")
    val code: String? = null,
    @Json(name = "Date")
    val date: String? = null,
    @Json(name = "Diff")
    val diff: String? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "Nominal")
    val nominal: String? = null,
    @Json(name = "Rate")
    val rate: String? = null
) {
    var exchangeDates: ExchangeDates? = null
}