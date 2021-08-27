package uz.boywonder.mymovies.ui.viewPager

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.ViewPagerAdapter
import uz.boywonder.mymovies.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding()

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

    }
}