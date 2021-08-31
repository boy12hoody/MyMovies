package uz.boywonder.mymovies.ui.actorDetails

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
import uz.boywonder.mymovies.adapters.MoviesByPersonAdapter
import uz.boywonder.mymovies.databinding.FragmentActorDetailsBinding
import uz.boywonder.mymovies.models.CastByPerson
import uz.boywonder.mymovies.util.Constants.Companion.BASE_BACKDROP_PATH
import uz.boywonder.mymovies.util.NetworkResult

@AndroidEntryPoint
class ActorDetailsFragment : Fragment(R.layout.fragment_actor_details),
    MoviesByPersonAdapter.OnItemClickListener {

    private val binding: FragmentActorDetailsBinding by viewBinding()
    private val actorDetailsViewModel: ActorDetailsViewModel by viewModels()
    private val moviesByPersonAdapter by lazy { MoviesByPersonAdapter(this) }
    private val args by navArgs<ActorDetailsFragmentArgs>()

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
        Log.d("ActorDetailsFragment", "requestApiData() Called")
        actorDetailsViewModel.getPersonResponse(args.cast.id)
        actorDetailsViewModel.personLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.apply {
                        binding.apply {
                            actorImageView.load(BASE_BACKDROP_PATH + profilePath) {
                                crossfade(600)
                                error(R.drawable.ic_no_image)
                            }
                            nameTextView.text = name
                            dateImageView.setColorFilter(
                                ContextCompat.getColor(
                                    dateImageView.context,
                                    R.color.green
                                )
                            )
                            dateTextView.text = birthday
                            locationImageView.setColorFilter(
                                ContextCompat.getColor(
                                    locationImageView.context,
                                    R.color.red
                                )
                            )
                            locationTextView.text = placeOfBirth
                            bioTextView.text = biography

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

        actorDetailsViewModel.getCreditsResponse(args.cast.id)
        actorDetailsViewModel.personCreditsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let { moviesByPersonAdapter.setNewData(it) }
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
        binding.creditsRecyclerView.apply {
            adapter = moviesByPersonAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun OnItemClick(castByPerson: CastByPerson) {
//        val action = ActorDetailsFragmentDirections.actionActorDetailsFragmentToMovieDetailsFragment(castByPerson.id)
//        findNavController().navigate(action)
    }
}