package uz.boywonder.mymovies.ui.navHosts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.FragmentSecondNavHostBinding


class SecondFragmentNavHost : Fragment(R.layout.fragment_second_nav_host) {

    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.tab_2_nav_host_fragment_container
    private val navGraphId = R.navigation.nav_graph_top_rated

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController
    }

}