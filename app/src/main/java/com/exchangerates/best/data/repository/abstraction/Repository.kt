package com.exchangerates.best.data.repository.abstraction

import kotlinx.coroutines.flow.Flow
import com.exchangerates.best.data.model.ExchangeDates
import com.exchangerates.best.data.model.RateItemData

interface Repository {

    //Register and login pages
    fun getList(date:String): Flow<List<RateItemData>>
    fun getGraphList(
        startHistoryData: String,
        endHistoryData: String,
        currency: String
    ): Flow<ExchangeDates>

}