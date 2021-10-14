package com.example.timer.fragment.timer.adapter

import android.content.*
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timer.R
import com.example.timer.model.Timer
import com.example.timer.service.TimerService

import com.example.timer.model.Sequence


class TimerObserver(
    private val timerList: Array<Timer>,
    context: Context,
    sequence: Sequence,
    firstInit: Boolean) {

    private var _title: MutableLiveData<String> = MutableLiveData(timerList[0].title)
    val title: LiveData<String>
        get() = _title

    private var _timerIndex: MutableLiveData<Int> = MutableLiveData(0)
    val timerIndex: LiveData<Int>
        get() = _timerIndex

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
            _timerIndex.value = mService.getIndex()
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
                _timerIndex.value = intent.getIntExtra(TimerService.TIMER_INDEX, 0)
                _remainingTime.value = intent.getIntExtra(TimerService.REMAINING_TIME, 0)
            }
            else if (intent.action == TimerService.FINISH) {
                player.start()
            }
        }
    }

    val player: MediaPlayer = MediaPlayer.create(context, R.raw.timer_sound)

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
        val filter = IntentFilter()
        filter.addAction(TimerService.TICK);
        filter.addAction(TimerService.FINISH);
        context.registerReceiver(receiver, filter)
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
            _title.value = mService.getTitle()
            _timerIndex.value = mService.getIndex()
            _remainingTime.value = mService.getRemainingTime()
        }
    }

    fun nextTimer(){
        if (mBound){
            mService.nextTimer()
            _title.value = mService.getTitle()
            _timerIndex.value = mService.getIndex()
            _remainingTime.value = mService.getRemainingTime()
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