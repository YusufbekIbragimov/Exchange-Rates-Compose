package uz.yusufbekibragimov.valyutauz.data.model


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Currencies(
    @Json(name = "UZS")
    val uZS: UZS
)