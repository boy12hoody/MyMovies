package uz.boywonder.mymovies.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.boywonder.mymovies.models.CastList
import uz.boywonder.mymovies.models.MovieDetails
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

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = QUERY_LANG_ENG
    ): Response<MovieDetails>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = QUERY_LANG_ENG
    ): Response<CastList>

}