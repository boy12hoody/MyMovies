package uz.boywonder.mymovies.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.boywonder.mymovies.models.MovieList
import uz.boywonder.mymovies.util.Constants.Companion.API_KEY
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_LANG_ENG

interface MoviesAPI {

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @QueryMap queries: Map<String, String>,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = QUERY_LANG_ENG
    ): Response<MovieList>

}