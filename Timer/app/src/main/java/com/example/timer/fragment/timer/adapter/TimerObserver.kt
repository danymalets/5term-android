package com.example.timer.fragment.timer.adapter

import android.R.attr
import android.content.*
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timer.model.Timer
import com.example.timer.service.TimerService
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences
import android.R.attr.key
import com.example.timer.model.Sequence
import com.google.gson.Gson


class TimerObserver(
    private val timerList: Array<Timer>,
    context: Context,
    sequence: Sequence,
    firstInit: Boolean) {

    private var _title: MutableLiveData<String> = MutableLiveData(timerList[0].title)
    val title: LiveData<String>
        get() = _title

    private var _remainingTime: MutableLiveData<Int> = MutableLiveData(timerList[0].duration)
    val remainingTime: LiveData<Int>
        get() = _remainingTime

    private var _status: MutableLiveData<TimerStatus> = MutableLiveData(TimerStatus.PAUSE)
    val status: LiveData<TimerStatus>
        get() = _status

    private lateinit var mService: TimerService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            mService = binder.getService()
            _status.value = mService.getStatus()
            _title.value = mService.getTitle()
            _remainingTime.value = mService.getRemainingTime()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private var receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == TimerService.TICK) {
                _title.value = intent.getStringExtra(TimerService.TITLE)
                _remainingTime.value = intent.getIntExtra(TimerService.DURATION, 0)
            }
        }
    }

    init{
        val intent = Intent(context, TimerService::class.java)

        if (firstInit){
            Log.d("TTTTTTTT", "first-init")
            start()
            intent.putParcelableArrayListExtra(TimerService.TIMER_LIST, timerList.toCollection(ArrayList()))
            context.startService(intent)
        }
        else{
            Log.d("TTTTTTTT", "not first-init")
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

    }

    fun register(context: Context){
        context.registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    fun unregister(context: Context){
        context.unregisterReceiver(receiver)
    }

    fun start(){
        if (mBound){
            mService.startTimer()
            _status.value = TimerStatus.RUN
        }
    }

    fun stop(){
        if (mBound){
            mService.stopTimer()
            _status.value = TimerStatus.PAUSE
        }
    }

    fun previousTimer(){
        if (mBound){
            mService.previousTimer()
        }
    }

    fun nextTimer(){
        if (mBound){
            mService.nextTimer()
        }
    }

    fun delete(context: Context){
        if (mBound){
            mService.deleteTimer()
        }
    }
}

const val PREFS_TIMER = "timer"
const val KEY_SEQUENCE = "sequence"

enum class TimerStatus{
    PAUSE, RUN
}