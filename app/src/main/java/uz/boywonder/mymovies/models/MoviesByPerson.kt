package uz.boywonder.mymovies.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesByPerson(
    @Json(name = "cast")
    val cast: List<CastByPerson> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class CastByPerson(
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @Json(name = "character")
    val character: String = "",
    @Json(name = "credit_id")
    val creditId: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "title")
    val title: String = ""
)