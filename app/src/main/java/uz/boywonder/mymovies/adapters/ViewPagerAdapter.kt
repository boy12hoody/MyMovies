package uz.boywonder.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.boywonder.mymovies.ui.popularTab.PopularFragment
import uz.boywonder.mymovies.ui.topRatedTab.TopRatedFragment
import uz.boywonder.mymovies.ui.upcomingTab.UpcomingFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> TopRatedFragment()
            2 -> UpcomingFragment()
            else -> PopularFragment()
        }
    }

}