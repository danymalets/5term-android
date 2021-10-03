package com.example.timer.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.database.Timer
import com.example.timer.database.TimerDao
import com.example.timer.database.TimerDatabase
import com.example.timer.database.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    val allTimers: LiveData<List<Timer>>
    private val timerRepository: TimerRepository

    init{
        Log.d("vm", "init")
        val userDao = TimerDatabase.getDatabase(application).timerDao()
        timerRepository = TimerRepository(userDao)
        allTimers = timerRepository.getAllTimers
    }

    fun addTimer(timer: Timer){
        viewModelScope.launch(Dispatchers.IO){
            timerRepository.addTimer(timer)
        }
    }
}