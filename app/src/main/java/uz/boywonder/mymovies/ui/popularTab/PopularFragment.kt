package uz.boywonder.mymovies.ui.popularTab

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.MoviesListAdapter
import uz.boywonder.mymovies.databinding.FragmentPopularBinding
import uz.boywonder.mymovies.models.Result
import uz.boywonder.mymovies.ui.MainViewModel
import uz.boywonder.mymovies.util.Constants.Companion.API_KEY
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_API_KEY
import uz.boywonder.mymovies.util.NetworkListener
import uz.boywonder.mymovies.util.NetworkResult

class PopularFragment : Fragment(R.layout.fragment_popular), MoviesListAdapter.OnItemClickListener {

    private val binding: FragmentPopularBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var networkListener: NetworkListener
    private val moviesListAdapter by lazy { MoviesListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                Log.d("NetworkListener", status.toString())

                when (status) {

                    true -> {
                        if (mainViewModel.isOffline) {
                            Snackbar.make(
                                requireView(), "Back Online.", Snackbar.LENGTH_SHORT
                            ).setAction("Okay") {}.show()

                            mainViewModel.isOffline = false
                        }
                    }

                    false -> {
                        Snackbar.make(
                            requireView(), "No Internet Connection.", Snackbar.LENGTH_SHORT
                        ).setAction("Okay") {}.show()

                        mainViewModel.isOffline = true
                    }

                }

                // Every time network status changes, reads local database first
                // readDatabase()

                // For Now
                requestApiData()
            }
        }
    }

    private fun requestApiData() {

        Log.d("PopularFragment", "requestApiData() Called")
        mainViewModel.moviesPopularResponse(applyQuery())
        mainViewModel.moviesPopularLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { moviesListAdapter.submitData(viewLifecycleOwner.lifecycle, it) }
                }
            }
        }
    }

    private fun applyQuery(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()


        queries[QUERY_API_KEY] = "<<$API_KEY>>"

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