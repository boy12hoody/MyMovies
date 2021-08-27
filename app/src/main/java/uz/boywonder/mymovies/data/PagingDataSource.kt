package uz.boywonder.mymovies.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.boywonder.mymovies.data.network.MoviesAPI
import uz.boywonder.mymovies.models.Result
import java.io.IOException

const val PAGE_INDEX = 1

class PagingDataSource(
    private val moviesAPI: MoviesAPI,
    private val queryMap: Map<String, String>
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: PAGE_INDEX

        return try {
            val response = moviesAPI.getPopular(queryMap)
            val movies = response.body()!!.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        TODO("Not yet implemented")
    }
}