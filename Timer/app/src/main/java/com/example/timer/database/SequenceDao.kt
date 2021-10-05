package com.example.timer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.timer.model.Sequence

@Dao
interface SequenceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSequence(sequence: Sequence)

    @Update
    suspend fun updateSequence(sequence: Sequence)

    @Delete
    fun deleteSequences(sequence: Sequence)

//    @Query("DELETE from timer_table")
//    suspend fun deleteAllTimers(timer: Timer)

    @Query("SELECT * FROM sequence_table ORDER BY id ASC")
    fun getAllSequences(): LiveData<List<Sequence>>
}