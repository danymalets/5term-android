package com.example.timer.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timer.model.Sequence

class TimerViewModel: ViewModel() {

    private val _title = MutableLiveData<String>("New Timer")
    val title: LiveData<String>
        get() = _title

    val color = MutableLiveData<Int>(Color.WHITE)

    private val _warmUp = MutableLiveData<Int>(1)
    val warmUp: LiveData<Int>
        get() = _warmUp

    private val _workout = MutableLiveData<Int>(2)
    val workout: LiveData<Int>
        get() = _workout

    private val _rest = MutableLiveData<Int>(3)
    val rest: LiveData<Int>
        get() = _rest

    private val _cycles = MutableLiveData<Int>(4)
    val cycles: LiveData<Int>
        get() = _cycles

    private val _cooldown = MutableLiveData<Int>(5)
    val cooldown: LiveData<Int>
        get() = _cooldown

    fun getTimer() = Sequence(
        0,
        title.value.toString(),
        color.value!!.toInt(),
        warmUp.value!!.toInt(),
        workout.value!!.toInt(),
        rest.value!!.toInt(),
        cycles.value!!.toInt(),
        cooldown.value!!.toInt()
    )
}