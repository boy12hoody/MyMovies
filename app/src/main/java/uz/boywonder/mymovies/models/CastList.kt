package uz.boywonder.mymovies.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastList(
    @Json(name = "cast")
    val cast: MutableList<Cast> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name = "cast_id")
    val castId: Int = 0,
    @Json(name = "character")
    val character: String = "",
    @Json(name = "credit_id")
    val creditId: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "order")
    val order: Int = 0,
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "profile_path")
    val profilePath: String? = null
)