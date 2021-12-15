package com.tours.myapplication

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tours.myapplication.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            println(destination)
            when(destination.id){
                R.id.login -> binding.bottomNavMenu.visibility = View.INVISIBLE
                R.id.registration -> binding.bottomNavMenu.visibility = View.INVISIBLE
                else ->  binding.bottomNavMenu.visibility = View.VISIBLE
            }
        }
    }
}
