package uz.yusufbekibragimov.valyutauz.data.model


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name ="currencies")
    val currencies: Currencies,
    @Json(name ="datetime")
    val datetime: String
)