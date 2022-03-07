package uz.yusufbekibragimov.valyutauz.data.repository.implementation

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yusufbekibragimov.valyutauz.data.cache.abstraction.UserPreferenceManager
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

    override fun getList(): Flow<List<RateItemData>> = flow {

        val data = networkService.getListRate()
        Log.d("TTT", "getList: $data")
        if (data != null) {
            emit(data)
        }
    }

}