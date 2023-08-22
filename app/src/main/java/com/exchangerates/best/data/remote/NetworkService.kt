package com.exchangerates.best.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import com.exchangerates.best.data.model.ExchangeDates
import com.exchangerates.best.data.model.RateItemData

interface NetworkService {

    @GET("com/arkhiv-kursov-valyut/json/all/{date}/")
    suspend fun getListRate(@Path("date") date:String): List<RateItemData>

    @GET
    suspend fun getGraphList(
        @Url url: String
    ): ExchangeDates

}