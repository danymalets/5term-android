package com.example.timer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTimer(timer: Timer)

    @Query("SELECT * FROM timer_table ORDER BY id ASC")
    fun getAllTimers(): LiveData<List<Timer>>
}