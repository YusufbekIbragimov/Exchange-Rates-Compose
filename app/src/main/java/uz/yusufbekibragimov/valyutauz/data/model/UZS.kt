package uz.yusufbekibragimov.valyutauz.data.model


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UZS(
    @Json(name = "code")
    val code: String,
    @Json(name = "value")
    val value: Double
)