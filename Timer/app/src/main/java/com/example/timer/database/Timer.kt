package com.example.timer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_table")
class Timer (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wormUp: Int,
    val workout: Int,
    val rest: Int,
    val coolDown: Int,
    val cycles: Int
)