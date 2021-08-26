package uz.boywonder.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.boywonder.mymovies.ui.navHosts.FirstFragmentNavHost
import uz.boywonder.mymovies.ui.navHosts.SecondFragmentNavHost
import uz.boywonder.mymovies.ui.navHosts.ThirdFragmentNavHost

class PagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> SecondFragmentNavHost()
            2 -> ThirdFragmentNavHost()
            else -> FirstFragmentNavHost()
        }
    }

}