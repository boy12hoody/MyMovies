package uz.boywonder.mymovies.ui.navHosts

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import uz.boywonder.mymovies.R


class SecondFragmentNavHost : Fragment(R.layout.fragment_second_nav_host) {

    private var navController: NavController? = null
    private val nestedNavHostFragmentId = R.id.tab_2_nav_host_fragment_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        listenOnBackPressed()
    }

    private fun listenOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private val callback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {

            if (navController?.currentDestination?.id == navController?.graph?.startDestination) {
                isEnabled = false
                requireActivity().onBackPressed()
                isEnabled = true
            } else {
                navController?.navigateUp()
            }

        }

    }

    override fun onResume() {
        super.onResume()
        callback.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        callback.isEnabled = false
    }

}