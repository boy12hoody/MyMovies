package uz.boywonder.mymovies.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieList(
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "results")
    val results: MutableList<Result> = mutableListOf(),
    @Json(name = "total_pages")
    val totalPages: Int = 0,
    @Json(name = "total_results")
    val totalResults: Int = 0
)