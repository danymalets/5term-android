package com.example.timer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timer.model.Sequence

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTimer(timer: Sequence)

    @Update
    suspend fun updateTimer(timer: Sequence)

    @Delete
    fun deleteTimer(timer: Sequence)

//    @Query("DELETE from timer_table")
//    suspend fun deleteAllTimers(timer: Timer)

    @Query("SELECT * FROM sequence_table ORDER BY id ASC")
    fun getAllTimers(): LiveData<List<Sequence>>
}