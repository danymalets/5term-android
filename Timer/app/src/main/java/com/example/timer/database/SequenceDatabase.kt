package com.example.timer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timer.model.Sequence

@Database(entities = [Sequence::class], version = 5)
abstract class SequenceDatabase: RoomDatabase() {
    abstract fun sequenceDao(): SequenceDao

    companion object {
        @Volatile
        private var INSTANCE: SequenceDatabase? = null

        fun getDatabase(context: Context): SequenceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SequenceDatabase::class.java,
                    "timer_database").fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}