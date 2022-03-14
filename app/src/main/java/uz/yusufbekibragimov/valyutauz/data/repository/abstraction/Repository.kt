package uz.yusufbekibragimov.valyutauz.data.repository.abstraction

import kotlinx.coroutines.flow.Flow
import uz.yusufbekibragimov.valyutauz.data.model.ExchangeDates
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import java.util.*

/**
 * Created by Yusufbek Ibragimov on 29/10/21.
 * Copyright (c) 2021  All rights reserved.
 **/

interface Repository {

    //Register and login pages
    fun getList(date:String): Flow<List<RateItemData>>
    fun getGraphList(
        startHistoryData: String,
        endHistoryData: String,
        currency: String
    ): Flow<ExchangeDates>

}