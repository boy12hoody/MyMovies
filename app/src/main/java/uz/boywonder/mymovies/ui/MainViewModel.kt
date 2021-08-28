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

    /* RETROFIT */

    fun getMoviesResponse(category: String, queries: Map<String, String>) = viewModelScope.launch {
        getMoviesResponseSafeCall(category, queries)
    }

    private suspend fun getMoviesResponseSafeCall(category: String, queries: Map<String, String>) {
        _moviesLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovies(category, queries)
                _moviesLiveData.value = handleMoviesResponse(response)
            } catch (e: Exception) {
                _moviesLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MainViewModel", e.message.toString())
            }

        }
    }

    private fun handleMoviesResponse(response: Response<MovieList>): NetworkResult<MovieList> {
        return when {
            response.body()!!.results.isNullOrEmpty() -> {
                NetworkResult.Error("No Movie Found.")
            }
            response.isSuccessful -> {
                val movieListData = response.body()
                NetworkResult.Success(movieListData!!)
            }
            else -> NetworkResult.Error(response.message())
        }
    }


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