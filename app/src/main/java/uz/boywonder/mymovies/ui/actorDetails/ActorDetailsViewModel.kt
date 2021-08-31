package uz.boywonder.mymovies.ui.actorDetails

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
import uz.boywonder.mymovies.models.MoviesByPerson
import uz.boywonder.mymovies.models.Person
import uz.boywonder.mymovies.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /* INIT VARIABLES */

    private var _personLiveData: MutableLiveData<NetworkResult<Person>> = MutableLiveData()
    val personLiveData: LiveData<NetworkResult<Person>> get() = _personLiveData

    private var _personCreditsLiveData: MutableLiveData<NetworkResult<MoviesByPerson>> =
        MutableLiveData()
    val personCreditsLiveData: LiveData<NetworkResult<MoviesByPerson>> get() = _personCreditsLiveData

    /* RETROFIT */

    fun getPersonResponse(personId: Int) = viewModelScope.launch {
        getPersonResponseSafeCall(personId)
    }

    private suspend fun getPersonResponseSafeCall(personId: Int) {
        _personLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPerson(personId)
                _personLiveData.value = handlePersonResponse(response)
            } catch (e: Exception) {
                _personLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("ActorDetailsViewModel", e.message.toString())
            }
        }
    }

    private fun handlePersonResponse(response: Response<Person>): NetworkResult<Person> {
        when {
            response.isSuccessful -> {
                val personData = response.body()
                return NetworkResult.Success(personData!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }

    /* GET CREDITS BY THIS PERSON */

    fun getCreditsResponse(personId: Int) = viewModelScope.launch {
        getCreditsResponseSafeCall(personId)
    }

    private suspend fun getCreditsResponseSafeCall(personId: Int) {
        _personCreditsLiveData.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPersonCredits(personId)
                _personCreditsLiveData.value = handleCreditsResponse(response)
            } catch (e: Exception) {
                _personCreditsLiveData.value = NetworkResult.Error("Something went wrong.")
                Log.e("MovieDetailsViewModel", e.message.toString())
            }
        }
    }

    private fun handleCreditsResponse(response: Response<MoviesByPerson>): NetworkResult<MoviesByPerson> {
        when {
            response.isSuccessful -> {
                val personCreditsData = response.body()
                return NetworkResult.Success(personCreditsData!!)
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