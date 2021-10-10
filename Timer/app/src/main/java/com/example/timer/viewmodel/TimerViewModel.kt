package com.example.timer.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.timer.fragment.TimerFragment
import com.example.timer.fragment.TimerFragmentArgs
import com.example.timer.model.Sequence
import com.example.timer.model.Timer
import com.example.timer.service.NotificationUtil
import com.example.timer.service.TimerService

class TimerViewModel(
    application: Application,
    private val sequence: Sequence,
    private val firstInit: Boolean): AndroidViewModel(application) {

    var duration = MutableLiveData(0)

    private val notificationUtil = NotificationUtil()

    private var receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == TimerService.TICK) {
                val title = intent.getStringExtra(TimerService.TITLE)
                duration.value = intent.getIntExtra(TimerService.DURATION, 0)
            }
        }
    }

    init{
        if (firstInit){
            TimerService.startTimerIfNotStarted(getApplication<Application>().applicationContext, ArrayList(toTimerList(sequence)))
        }
    }

    fun onForeground(){
        getApplication<Application>().applicationContext.registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    fun onReForeground(){
        //NotificationUtil.stopNotification(getApplication<Application>().applicationContext)
    }

    fun onBackground(){
        getApplication<Application>().applicationContext.unregisterReceiver(receiver)
        notificationUtil.startNotification(getApplication<Application>().applicationContext)
    }

    companion object{
        fun toTimerList(sequence: Sequence): List<Timer>{
            val list = mutableListOf(Timer(TimerFragment.WARM_UP, sequence.warmUp))
            for (i in 1 until sequence.cycles){
                list.add(Timer(TimerFragment.WORKOUT, sequence.workout))
                list.add(Timer(TimerFragment.REST, sequence.rest))
            }
            list.add(Timer(TimerFragment.WORKOUT, sequence.workout))
            list.add(Timer(TimerFragment.COOLDOWN, sequence.cooldown))
            return list
        }
    }
}

class TimerViewModelFactory(
    private val application: Application,
    private val sequence: Sequence,
    private val firstInit: Boolean,
) : ViewModelProvider.AndroidViewModelFactory(application)  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(application, sequence, firstInit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}