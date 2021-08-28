package uz.boywonder.mymovies.ui.popularTab

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
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.MoviesListAdapter
import uz.boywonder.mymovies.databinding.FragmentPopularBinding
import uz.boywonder.mymovies.models.Result
import uz.boywonder.mymovies.ui.MainViewModel
import uz.boywonder.mymovies.util.Constants.Companion.CAT_POPULAR
import uz.boywonder.mymovies.util.NetworkResult

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular), MoviesListAdapter.OnItemClickListener {

    private val binding: FragmentPopularBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private val moviesListAdapter by lazy { MoviesListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Every time network status changes, reads local database first
        // readDatabase()


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                setupRecyclerView()

                // For Now
                requestApiData()
            }
        }

    }

    private fun requestApiData() {
        Log.d("PopularFragment", "requestApiData() Called")
        mainViewModel.getMoviesResponse(CAT_POPULAR, applyQuery())
        mainViewModel.moviesLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { moviesListAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun applyQuery(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        return queries
    }

    private fun setupRecyclerView() {
        binding.recyclerview.apply {
            adapter = moviesListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }

    }

    override fun OnItemClick(result: Result) {
        TODO() //navigate to MovieDetailsFragment
    }
}