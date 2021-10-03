package com.example.timer.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.timer.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}