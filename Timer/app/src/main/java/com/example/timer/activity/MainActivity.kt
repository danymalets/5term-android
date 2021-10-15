package com.example.timer.activity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.timer.R
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val language = prefs.getString(getString(R.string.language), "en")
        val resources: Resources = (this as Context).resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration

        config.setLocale(Locale(language!!.lowercase(Locale.ROOT)))

        val fontScale = prefs.getString(getString(R.string.font_scale), "1")!!.toFloat()
        config.fontScale = fontScale

        resources.updateConfiguration(config, dm)


        super.onCreate(savedInstanceState)


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