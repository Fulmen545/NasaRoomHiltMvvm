package com.fulmen.nasaroom

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fulmen.nasaroom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.NasaListFragment, R.id.FavoriteFragment
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.NasaListFragment -> bottomNavigateTo(R.id.NasaListFragment)
                R.id.FavoriteFragment -> bottomNavigateTo(R.id.FavoriteFragment)
            }
            true
        }
    }

    private fun bottomNavigateTo(@IdRes id: Int) {
        navController.navigate(id, null, clearBackstackNavOptions(id))
    }

    private fun clearBackstackNavOptions(@IdRes id: Int) = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(id, true).build()
}