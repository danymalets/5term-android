package com.example.timer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.database.TimerDatabase
import com.example.timer.model.Sequence
import com.example.timer.repository.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.List

class TimerListViewModel(application: Application) : AndroidViewModel(application) {

    val allTimers: LiveData<List<Sequence>>
    private val timerRepository: TimerRepository

    init{
        Log.d("vm", "init")
        val timerDao = TimerDatabase.getDatabase(application).timerDao()
        timerRepository = TimerRepository(timerDao)
        allTimers = timerRepository.getAllTimers
    }

    fun addTimer(timer: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            timerRepository.addTimer(timer)
        }
    }

    fun updateTimer(timer: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            timerRepository.updateTimer(timer)
        }
    }

    fun deleteTimer(timer: Sequence){
        viewModelScope.launch(Dispatchers.IO){
            timerRepository.deleteTimer(timer)
        }
    }
}