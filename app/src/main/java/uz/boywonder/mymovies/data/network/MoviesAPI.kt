package uz.boywonder.mymovies.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import uz.boywonder.mymovies.models.MovieList

interface MoviesAPI {

    @GET("movie/popular/")
    suspend fun getPopular(
        @QueryMap queries: Map<String, String>
    ): Response<MovieList>

}