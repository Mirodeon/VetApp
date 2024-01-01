package com.mirodeon.vetapp.activity.main.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mirodeon.vetapp.utils.extension.forEachUntil

class NavigationManager(
    activity: AppCompatActivity,
    topLvlIds: Set<Int>,
    addId: Int,
    navGraphId: Int,
    toolbar: Toolbar?,
    bottomBar: BottomNavigationView?,
    titleView: TextView?,
    fragments: Map<Int, String>,
    addButton: View?,
    noTitle: String
) {
    val navController: NavController

    init {
        navController = activity.findNavController(navGraphId)
        setNavigation(activity, toolbar, topLvlIds, bottomBar)
        changeOnDestination(fragments, titleView, toolbar, noTitle)
        setNavigationToolBar(addButton, addId)
    }

    private fun setNavigation(
        activity: AppCompatActivity,
        toolbar: Toolbar?,
        topLvlIds: Set<Int>,
        bottomBar: BottomNavigationView?
    ) {
        activity.setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(
            activity, navController, AppBarConfiguration(topLvlIds)
        )

        bottomBar?.let {
            NavigationUI.setupWithNavController(
                it,
                navController
            )
        }

    }

    private fun changeOnDestination(
        fragments: Map<Int, String>,
        titleView: TextView?,
        toolbar: Toolbar?,
        noTitle: String
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->

            var destinationFound = false
            fragments.forEachUntil { fragment ->
                if (destination.id == fragment.key) {
                    titleView?.text = fragment.value
                    toolbar?.visibility = View.VISIBLE
                    destinationFound = true
                }
                destinationFound
            }

            if (!destinationFound) {
                titleView?.text = noTitle
                toolbar?.visibility = View.INVISIBLE
            }
        }
    }

    private fun setNavigationToolBar(
        addButton: View?,
        addId: Int
    ) {
        addButton?.setOnClickListener {
            navController.navigate(addId)
        }
    }
}