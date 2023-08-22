package com.exchangerates.best.data.repository.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.exchangerates.best.data.cache.abstraction.UserPreferenceManager
import com.exchangerates.best.data.model.ExchangeDates
import com.exchangerates.best.data.model.RateItemData
import com.exchangerates.best.data.remote.NetworkService
import com.exchangerates.best.data.repository.abstraction.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val prefs: UserPreferenceManager,
) : Repository {

    override fun getList(date: String): Flow<List<RateItemData>> = flow {
        val data = networkService.getListRate(date)
        emit(data)
    }

    override fun getGraphList(
        startHistoryData: String,
        endHistoryData: String,
        currency: String
    ): Flow<ExchangeDates> = flow {
        val data = networkService.getGraphList("https://api.currencyapi.com/v3/range?apikey=Nccr5y4w4ASy3ccL8sfAbURivFkqq2rtptGcWvuG&datetime_start=${startHistoryData}&datetime_end=${endHistoryData}&base_currency=${currency}&currencies=UZS")
        if (data != null) {
            emit(data)
        }
    }

}