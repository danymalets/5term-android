package com.example.timer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sequence_table")
class Sequence(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val color: Int,
    val warmUp: Int,
    val workout: Int,
    val rest: Int,
    val cycles: Int,
    val coolDown: Int
): Parcelable{

    fun totalDuration() = warmUp +
            workout * cycles +
            rest * (cycles - 1) +
            coolDown
}