package com.example.timer.model

data class TimerInformation(
    val wormUp: Int,
    val workout: Int,
    val rest: Int,
    val coolDown: Int,
    val cycles: Int) {

}