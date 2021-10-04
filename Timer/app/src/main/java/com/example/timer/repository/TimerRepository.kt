package com.example.timer.repository

import androidx.lifecycle.LiveData
import com.example.timer.model.Sequence
import com.example.timer.database.TimerDao

class TimerRepository(private val timerDao: TimerDao) {

    val getAllTimers: LiveData<List<Sequence>> = timerDao.getAllTimers()

    suspend fun addTimer(timer: Sequence){
        timerDao.addTimer(timer)
    }

    suspend fun updateTimer(timer: Sequence){
        timerDao.updateTimer(timer)
    }

    suspend fun deleteTimer(timer: Sequence){
        timerDao.deleteTimer(timer)
    }
}