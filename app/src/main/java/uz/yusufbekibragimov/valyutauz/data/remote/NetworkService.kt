package uz.yusufbekibragimov.valyutauz.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData

/**
 * Created by Abdulaziz Rasulbek on 02/10/21.
 * Copyright (c) 2021  All rights reserved.
 **/
interface NetworkService {

    @GET("uz/arkhiv-kursov-valyut/json/all/")
    suspend fun getListRate(): List<RateItemData>

}