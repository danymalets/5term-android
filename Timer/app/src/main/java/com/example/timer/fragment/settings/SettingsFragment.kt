package com.example.timer.fragment.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import com.example.timer.databinding.FragmentEditSequenceBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.timer.R
import com.example.timer.fragment.sequencelist.SequenceListViewModel
import com.example.timer.repository.SequenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.timer.R.*
import java.util.*


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val sequenceListViewModel: SequenceListViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(xml.preferences, rootKey)

        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)

        val button: Preference? = findPreference(getString(string.delete_all))
        button?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(getString(string.delete_confirmation))
                .setCancelable(false)
                .setPositiveButton(getString(string.yes)) { _, _ ->
                    sequenceListViewModel.deleteAllSequence()
                }
                .setNegativeButton(getString(string.no)) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle(getString(string.confirmation))
            alert.show()
            true
        }
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        when (key){
            activity?.getString(R.string.dark_theme) -> {
                activity?.recreate()
            }
            activity?.getString(R.string.language) ->{
                val locale = Locale("ru")
                Locale.setDefault(locale)
                activity?.recreate()
            }
            activity?.getString(R.string.font_scale) ->{
                activity?.recreate()
            }
        }
    }

}