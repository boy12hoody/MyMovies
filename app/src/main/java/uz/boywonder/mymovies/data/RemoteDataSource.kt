package uz.boywonder.mymovies.data

import retrofit2.Response
import uz.boywonder.mymovies.data.network.MoviesAPI
import uz.boywonder.mymovies.models.CastList
import uz.boywonder.mymovies.models.MovieDetails
import uz.boywonder.mymovies.models.MovieList
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesAPI: MoviesAPI
) {
    suspend fun getMovies(category: String, queries: Map<String, String>): Response<MovieList> {
        return moviesAPI.getMovies(category, queries)
    }

    suspend fun getMovie(movieId: Int): Response<MovieDetails> {
        return moviesAPI.getMovie(movieId)
    }

    suspend fun getCredits(movieId: Int): Response<CastList> {
        return moviesAPI.getCredits(movieId)
    }
}