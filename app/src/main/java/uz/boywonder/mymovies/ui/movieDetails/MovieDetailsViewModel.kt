package uz.boywonder.mymovies.ui.movieDetails

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
import uz.boywonder.mymovies.models.CastList
import uz.boywonder.mymovies.models.MovieDetails
import uz.boywonder.mymovies.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /* INIT VARIABLES */

    private var _movieLiveData: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()
    val movieLiveData: LiveData<NetworkResult<MovieDetails>> get() = _movieLiveData

    private var _castLiveData: MutableLiveData<NetworkResult<CastList>> = MutableLiveData()
    val castLiveData: MutableLiveData<NetworkResult<CastList>> get() = _castLiveData

    /* RETROFIT */

    /* GET A MOVIE */

    fun getMovieResponse(movieId: Int) = viewModelScope.launch {
        getMovieResponseSafeCall(movieId)
    }

    private suspend fun getMovieResponseSafeCall(movieId: Int) {
        _movieLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMovie(movieId)
                _movieLiveData.value = handleMovieResponse(response)
            } catch (e: Exception) {
                _movieLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MovieDetailsViewModel", e.message.toString())
            }

        }
    }

    private fun handleMovieResponse(response: Response<MovieDetails>): NetworkResult<MovieDetails> {
        when {
            response.isSuccessful -> {
                val movieData = response.body()
                return NetworkResult.Success(movieData!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }

    /* GET CAST */

    fun getCastResponse(movieId: Int) = viewModelScope.launch {
        getCastResponseSafeCall(movieId)
    }

    private suspend fun getCastResponseSafeCall(movieId: Int) {
        _castLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCredits(movieId)
                _castLiveData.value = handleCastResponse(response)
            } catch (e: Exception) {
                _castLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MovieDetailsViewModel", e.message.toString())
            }
        }
    }

    private fun handleCastResponse(response: Response<CastList>): NetworkResult<CastList> {
        when {
            response.isSuccessful -> {
                val castData = response.body()
                return NetworkResult.Success(castData!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }


    // checking internet connection. returns true or false.

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