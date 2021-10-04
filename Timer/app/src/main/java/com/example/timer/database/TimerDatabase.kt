package com.example.timer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timer.model.Sequence

@Database(entities = [Sequence::class], version = 3)
abstract class TimerDatabase: RoomDatabase() {
    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerDatabase? = null

        fun getDatabase(context: Context): TimerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TimerDatabase::class.java,
                    "timer_database").fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}