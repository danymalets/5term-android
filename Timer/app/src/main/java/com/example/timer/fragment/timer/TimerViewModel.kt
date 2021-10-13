package com.example.timer.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.navigation.NavArgs
import com.example.timer.model.Timer
import com.example.timer.fragment.timer.adapter.NotificationAdapter
import com.example.timer.fragment.timer.adapter.TimerObserver
import com.example.timer.model.Sequence

class TimerViewModel(
    application: Application,
    private val sequence: Sequence,
    timerList: Array<Timer>,
    firstInit: Boolean): AndroidViewModel(application) {

    var timerObserver: TimerObserver

    private val context: Context
        get() = getApplication<Application>().applicationContext

    private val notificationAdapter = NotificationAdapter()

    init{
        timerObserver = TimerObserver(timerList, context, sequence, firstInit)
        timerObserver.register(context)
    }

    fun start() = timerObserver.start()

    fun stop() = timerObserver.stop()

    fun stopNotification(){
        notificationAdapter.tryStopNotification(context)
    }

    fun startNotification(){
        notificationAdapter.startNotification(sequence, context)
    }

    fun deleteTimer(){
        notificationAdapter.tryStopNotification(context)
        timerObserver.delete(context)
    }

    override fun onCleared() {
        notificationAdapter.tryStopNotification(context)
        timerObserver.unregister(context)
    }
}

class TimerViewModelFactory(
    private val application: Application,
    private val sequence: Sequence,
    private val timerList: Array<Timer>,
    private val firstInit: Boolean
) : ViewModelProvider.AndroidViewModelFactory(application)  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(application, sequence, timerList, firstInit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}