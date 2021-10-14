package com.example.timer.repository

import androidx.lifecycle.LiveData
import com.example.timer.model.Sequence
import com.example.timer.database.SequenceDao

class SequenceRepository(private val timerDao: SequenceDao) {

    fun getAllTimers(): LiveData<List<Sequence>> = timerDao.getAllSequences()

    suspend fun addSequence(timer: Sequence){
        timerDao.addSequence(timer)
    }

    suspend fun updateSequence(timer: Sequence){
        timerDao.updateSequence(timer)
    }

    suspend fun deleteAllSequences(){
        timerDao.deleteAllSequences()
    }

    suspend fun deleteSequence(timer: Sequence){
        timerDao.deleteSequence(timer)
    }
}