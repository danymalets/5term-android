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
    var title: String,
    var color: Int,
    var warmUp: Int,
    var workout: Int,
    var rest: Int,
    var cycles: Int,
    var cooldown: Int
): Parcelable{

    fun totalDuration() = warmUp +
            workout * cycles +
            rest * (cycles - 1) +
            cooldown
}