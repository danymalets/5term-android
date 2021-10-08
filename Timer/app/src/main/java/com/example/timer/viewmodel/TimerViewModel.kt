package com.example.timer.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timer.model.Sequence
import com.example.timer.model.Timer
import com.example.timer.service.TimerService

class TimerViewModel(): ViewModel() {
    lateinit var sequence: Sequence

}