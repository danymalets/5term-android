package com.example.timer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.timer.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val darkTheme = prefs.getBoolean(getString(R.string.dark_theme), false)
        if (darkTheme)
            setTheme(R.style.Theme_TimerDark)
        else
            setTheme(R.style.Theme_Timer)

        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.sequenceListFragment,
                R.id.timerFragment
            )
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}