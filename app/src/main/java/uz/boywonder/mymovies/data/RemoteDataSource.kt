package uz.boywonder.mymovies.data

import retrofit2.Response
import uz.boywonder.mymovies.data.network.MoviesAPI
import uz.boywonder.mymovies.models.MovieList
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesAPI: MoviesAPI
) {
    suspend fun getPopular(queries: Map<String, String>): Response<MovieList> {
        return moviesAPI.getPopular(queries)
    }
}