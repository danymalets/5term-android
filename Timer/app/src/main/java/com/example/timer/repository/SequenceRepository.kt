package com.example.timer.repository

import androidx.lifecycle.LiveData
import com.example.timer.model.Sequence
import com.example.timer.database.SequenceDao

class SequenceRepository(private val timerDao: SequenceDao) {

    val getAllTimers: LiveData<List<Sequence>> = timerDao.getAllSequences()

    suspend fun addSequence(timer: Sequence){
        timerDao.addSequence(timer)
    }

    suspend fun updateSequence(timer: Sequence){
        timerDao.updateSequence(timer)
    }

    suspend fun deleteSequence(timer: Sequence){
        timerDao.deleteSequences(timer)
    }
}