package uz.boywonder.mymovies.models


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class MovieList(
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "results")
    val movieResults: MutableList<MovieResult> = mutableListOf(),
    @Json(name = "total_pages")
    val totalPages: Int = 0,
    @Json(name = "total_results")
    val totalResults: Int = 0
)

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieResult(
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "poster_path")
    val posterPath: String = "",
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "video")
    val video: Boolean = false,
) : Parcelable