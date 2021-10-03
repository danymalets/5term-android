package com.example.timer.database

import androidx.lifecycle.LiveData

class TimerRepository(private val timerDao: TimerDao) {

    val getAllTimers: LiveData<List<Timer>> = timerDao.getAllTimers()

    suspend fun addTimer(timer: Timer){
        timerDao.addTimer(timer)
    }
}