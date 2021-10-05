package com.example.timer.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timer.model.Sequence
import com.example.timer.model.Timer
import com.example.timer.service.TimerService

class TimerViewModel(private val context: Context, private val sequence: Sequence): ViewModel() {

    private lateinit var receiver: BroadcastReceiver

    init{
        Intent(context, TimerService::class.java).also { intent ->
            intent.putParcelableArrayListExtra(TimerService.TIMER_LIST, ArrayList(toTimerList()))
            context.startService(intent)
        }

        Log.d("vm", "start")


        receiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == "TICK") {
                    val title = intent.getStringExtra(TimerService.TITLE)
                    val duration = intent.getIntExtra(TimerService.DURATION, 0)
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    fun toTimerList(): List<Timer>{
        val list = mutableListOf(Timer(WARM_UP, sequence.warmUp))
        for (i in 1 until sequence.cycles){
            list.add(Timer(WORKOUT, sequence.workout))
            list.add(Timer(REST, sequence.rest))
        }
        list.add(Timer(WORKOUT, sequence.workout))
        list.add(Timer(COOLDOWN, sequence.cooldown))
        return list
    }

    companion object{
        const val WARM_UP = "Warm-up"
        const val WORKOUT = "Workout"
        const val REST = "Rest"
        const val COOLDOWN = "Cooldown"
    }
}

class TimerViewModelFactory(private val context: Context, private val sequence: Sequence) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(context, sequence) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}