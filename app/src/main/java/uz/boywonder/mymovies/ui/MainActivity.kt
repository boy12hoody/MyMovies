package uz.boywonder.mymovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.adapters.PagerAdapter
import uz.boywonder.mymovies.databinding.ActivityMainBinding
import uz.boywonder.mymovies.ui.popularTab.PopularFragment
import uz.boywonder.mymovies.ui.topRatedTab.TopRatedFragment
import uz.boywonder.mymovies.ui.upcomingTab.UpcomingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

    }


}