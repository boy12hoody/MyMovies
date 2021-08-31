package uz.boywonder.mymovies.ui.movieDetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.CastListAdapter
import uz.boywonder.mymovies.databinding.FragmentMovieDetailsBinding
import uz.boywonder.mymovies.models.Cast
import uz.boywonder.mymovies.util.Constants.Companion.BASE_BACKDROP_PATH
import uz.boywonder.mymovies.util.NetworkResult
import java.text.NumberFormat
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalTime
@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details),
    CastListAdapter.OnItemClickListener {

    private val binding: FragmentMovieDetailsBinding by viewBinding()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val castListAdapter by lazy { CastListAdapter(this) }
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                requestApiData()
            }
        }
    }


    private fun requestApiData() {
        Log.d("MovieDetailsFragment", "requestApiData() Called")
        movieDetailsViewModel.getMovieResponse(args.movieId)
        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.apply {

                        binding.apply {
                            movieImageImageView.load(BASE_BACKDROP_PATH + posterPath) {
                                crossfade(600)
                                error(R.drawable.ic_no_image)
                            }
                            popularityImageView.setColorFilter(
                                ContextCompat.getColor(
                                    popularityImageView.context,
                                    R.color.green
                                )
                            )
                            popularityTextView.text = popularity.toString()
                            runtimeImageView.setColorFilter(
                                ContextCompat.getColor(
                                    popularityImageView.context,
                                    R.color.yellow
                                )
                            )
                            runtimeTextView.text =
                                runtime.toDuration(DurationUnit.MINUTES).toString()
                            titleTextView.text = title
                            dateTextView.text = releaseDate
                            overviewTextView.text = overview

                            val numberFormat = NumberFormat.getCurrencyInstance()
                            numberFormat.maximumFractionDigits = 0
                            revenueTextView.text = numberFormat.format(revenue)
                        }
                    }
                }
                is NetworkResult.Error -> {

                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    // shimmer effect. maybe later
                }
            }
        }

        movieDetailsViewModel.getCastResponse(args.movieId)
        movieDetailsViewModel.castLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { castListAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {

                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    // shimmer effect. maybe later
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.crewRecyclerView.apply {
            adapter = castListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun OnItemClick(cast: Cast) {
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToActorDetailsFragment(cast)
        findNavController().navigate(action)
    }

}