package com.example.timer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Timer(val title: String, val duration: Int): Parcelable {

}