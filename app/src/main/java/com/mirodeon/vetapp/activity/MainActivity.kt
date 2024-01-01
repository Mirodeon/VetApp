package com.mirodeon.vetapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mirodeon.vetapp.R
import com.mirodeon.vetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setNavigation() {
        /*navController = findNavController(R.id.navHostFragment)
        binding?.bottomNavigationView?.let {
            NavigationUI.setupWithNavController(
                it,
                navController
            )
        }*/
    }
}