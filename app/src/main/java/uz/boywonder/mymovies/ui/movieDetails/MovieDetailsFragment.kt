package uz.boywonder.mymovies.ui.movieDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.FragmentMovieDetailsBinding
import uz.boywonder.mymovies.util.NetworkResult

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val binding: FragmentMovieDetailsBinding by viewBinding()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                requestApiData()
            }
        }
    }

    private fun requestApiData() {
        Log.d("MovieDetailsFragment", "requestApiData() Called")
        movieDetailsViewModel.getMovieResponse(args.movieResult.id)
        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    binding.apply {}
                }
                is NetworkResult.Error -> {

                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

}