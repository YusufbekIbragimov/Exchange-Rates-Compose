package uz.yusufbekibragimov.valyutauz.data.repository.abstraction

import kotlinx.coroutines.flow.Flow
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData

/**
 * Created by Yusufbek Ibragimov on 29/10/21.
 * Copyright (c) 2021  All rights reserved.
 **/

interface Repository {

    //Register and login pages
    fun getList(): Flow<List<RateItemData>>

}