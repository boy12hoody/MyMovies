package uz.boywonder.mymovies.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.boywonder.mymovies.data.Repository
import uz.boywonder.mymovies.models.MovieList
import uz.boywonder.mymovies.util.Constants.Companion.CAT_POPULAR
import uz.boywonder.mymovies.util.Constants.Companion.CAT_TOP_RATED
import uz.boywonder.mymovies.util.Constants.Companion.CAT_UPCOMING
import uz.boywonder.mymovies.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /* INIT VARIABLES */

    private var _moviesLiveData: MutableLiveData<NetworkResult<MovieList>> = MutableLiveData()
    val moviesLiveData: LiveData<NetworkResult<MovieList>> get() = _moviesLiveData
    private var movieListData: MovieList? = null

    var moviesPopularPage = 1
    var moviesTopRatedPage = 1
    var moviesUpcomingPage = 1

    /* RETROFIT */

    fun getMoviesResponse(category: String, queries: Map<String, String>) = viewModelScope.launch {
        getMoviesResponseSafeCall(category, queries)
    }

    private suspend fun getMoviesResponseSafeCall(category: String, queries: Map<String, String>) {
        _moviesLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovies(category, queries)
                _moviesLiveData.value = handleMoviesResponse(category, response)
            } catch (e: Exception) {
                _moviesLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MainViewModel", e.message.toString())
            }

        }
    }

    private fun handleMoviesResponse(
        category: String,
        response: Response<MovieList>
    ): NetworkResult<MovieList> {

        if (response.isSuccessful) {
            response.body()?.let { responseResult ->
                when (category) {
                    CAT_POPULAR -> {
                        moviesPopularPage++
                    }
                    CAT_TOP_RATED -> {
                        moviesTopRatedPage++
                    }
                    CAT_UPCOMING -> {
                        moviesUpcomingPage++
                    }
                }

                if (movieListData == null) {
                    movieListData = response.body()
                } else {
                    val oldMovieListData = movieListData?.movieResults
                    val newMovieListData = response.body()?.movieResults
                    oldMovieListData?.addAll(newMovieListData!!)
                }
                return NetworkResult.Success(movieListData ?: responseResult)
            }
        } else if (response.body()!!.movieResults.isNullOrEmpty()) {
            return NetworkResult.Error("No Movie Found.")
        }
        return NetworkResult.Error(response.message())
    }

    /* Handle View Pager State */


    // checking internet connection. returns true or false.

    var isOffline = false

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}