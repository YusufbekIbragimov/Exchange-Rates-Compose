package uz.yusufbekibragimov.valyutauz.data.repository.implementation

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yusufbekibragimov.valyutauz.data.cache.abstraction.UserPreferenceManager
import uz.yusufbekibragimov.valyutauz.data.model.ExchangeDates
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.data.remote.NetworkService
import uz.yusufbekibragimov.valyutauz.data.repository.abstraction.Repository
import javax.inject.Inject

/**
 * Created by Abdulaziz Rasulbek on 29/10/21.
 * Copyright (c) 2021  All rights reserved.
 **/
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
        val data =
            networkService.getGraphList("https://api.currencyapi.com/v3/range?apikey=Nccr5y4w4ASy3ccL8sfAbURivFkqq2rtptGcWvuG&datetime_start=${startHistoryData}&datetime_end=${endHistoryData}&base_currency=${currency}&currencies=UZS")
        if (data != null) {
            emit(data)
        }
    }

}