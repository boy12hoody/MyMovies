package uz.boywonder.mymovies.ui.popularTab

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.MoviesListAdapter
import uz.boywonder.mymovies.databinding.FragmentPopularBinding
import uz.boywonder.mymovies.models.Result
import uz.boywonder.mymovies.ui.MainViewModel
import uz.boywonder.mymovies.util.Constants.Companion.CAT_POPULAR
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_LANGUAGE
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_LANG_ENG
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_PAGE_NUMBER
import uz.boywonder.mymovies.util.Constants.Companion.QUERY_PAGE_SIZE
import uz.boywonder.mymovies.util.NetworkResult

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular), MoviesListAdapter.OnItemClickListener {

    private val binding: FragmentPopularBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private val moviesListAdapter by lazy { MoviesListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

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
                    val totalPages = response.data?.totalPages!! / QUERY_PAGE_SIZE + 2
                    isLastPage = mainViewModel.moviesPopularPage == totalPages
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
        queries[QUERY_PAGE_NUMBER] = mainViewModel.moviesPopularPage.toString()
        return queries
    }

    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLastPage = !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                mainViewModel.getMoviesResponse(CAT_POPULAR, applyQuery())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.apply {
            adapter = moviesListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addOnScrollListener(this@PopularFragment.scrollListener)
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