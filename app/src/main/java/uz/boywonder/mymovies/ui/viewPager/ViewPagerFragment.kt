package uz.boywonder.mymovies.ui.viewPager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.ViewPagerAdapter
import uz.boywonder.mymovies.databinding.FragmentViewPagerBinding
import uz.boywonder.mymovies.ui.MainViewModel
import uz.boywonder.mymovies.util.NetworkListener

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var networkListener: NetworkListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = ArrayList<String>()
        titles.add(getString(R.string.tab_popular))
        titles.add(getString(R.string.tab_top_rated))
        titles.add(getString(R.string.tab_upcoming))

        val pagerAdapter = ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        binding.apply {
            viewPager2.adapter = pagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }

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
            }
        }

    }
}