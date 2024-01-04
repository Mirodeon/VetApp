package com.mirodeon.vetapp.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mirodeon.vetapp.R
import com.mirodeon.vetapp.activity.main.utils.NavigationManager
import com.mirodeon.vetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navigationManager = NavigationManager(
            this,
            setOf(
                R.id.dosageFragment,
                R.id.favDosageFragment
            ),
            R.id.addDosageFragment,
            R.id.navHostFragment,
            binding?.toolbarMenu?.root,
            binding?.bottomNavigationView,
            binding?.toolbarMenu?.titleToolbar,
            mapOf(
                R.id.dosageFragment to getString(R.string.dosage_title),
                R.id.favDosageFragment to getString(R.string.fav_dosage_title),
                R.id.detailsDosageFragment to getString(R.string.details_dosage_title),
                R.id.addDosageFragment to getString(R.string.add_dosage_title)
            ),
            binding?.toolbarMenu?.btnActionBar,
            binding?.toolbarMenu?.btnSave,
            getString(R.string.no_title)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationManager.navController.navigateUp()
    }
}