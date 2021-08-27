package uz.boywonder.mymovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import retrofit2.Response
import uz.boywonder.mymovies.data.network.MoviesAPI
import uz.boywonder.mymovies.models.MovieList
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesAPI: MoviesAPI
) {
    fun getPopular(queries: Map<String, String>) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingDataSource(moviesAPI, queries)}
        ).flow
}