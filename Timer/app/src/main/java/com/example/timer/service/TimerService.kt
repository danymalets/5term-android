package com.example.timer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import com.example.timer.fragment.timer.utils.TimerStatus
import com.example.timer.model.Timer


class TimerService : Service() {

    lateinit var timerList: List<Timer>
    var timerIndex = 0
    private var deleted = false

    private var timer: CountDownTimer? = null
    private var remainingTime: Long = 0

    private val binder = LocalBinder()

    private var status = TimerStatus.PAUSE

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerIndex = 0
        timerList = intent?.getParcelableArrayListExtra(TIMER_LIST)!!
        remainingTime = timerList[timerIndex].duration * 1000L
        Log.d("timer-service", "start")
        return START_NOT_STICKY
    }

    fun getStatus() = status
    fun getIndex() = timerIndex
    fun getRemainingTime(): Int = ((remainingTime + 500) / 1000).toInt()
    fun getTitle() = timerList[timerIndex].title

    fun startTimer(){
        status = TimerStatus.RUN
        timer = object: CountDownTimer(remainingTime, 1) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val intent = Intent()
                intent.action = TICK
                intent.putExtra(TITLE, timerList[timerIndex].title)
                intent.putExtra(TIMER_INDEX, timerIndex)
                intent.putExtra(REMAINING_TIME, getRemainingTime())
                sendBroadcast(intent)
            }

            override fun onFinish() {
                val intent = Intent()
                intent.action = FINISH
                sendBroadcast(intent)

                timerIndex++
                if (timerIndex == timerList.size){
                }
                else{
                    remainingTime = timerList[timerIndex].duration * 1000L
                    startTimer()
                }
            }
        }.start()
    }

    fun stopTimer(){
        Log.d("stop", "IIIIIIIIIIIIIII")
        status = TimerStatus.PAUSE
        timer?.cancel()
    }

    fun previousTimer(){
        val needStart = status == TimerStatus.RUN
        stopTimer()
        if (timerIndex > 0){
            timerIndex--
        }
        remainingTime = timerList[timerIndex].duration * 1000L
        if (needStart) startTimer()
    }

    fun nextTimer(){
        val needStart = status == TimerStatus.RUN
        stopTimer()
        if (timerIndex < timerList.size - 1){
            timerIndex++
        }
        remainingTime = timerList[timerIndex].duration * 1000L
        if (needStart) startTimer()
    }

    fun deleteTimer(){
        stopTimer()
        deleted = true;
    }

    companion object{
        var instance: TimerService? = null

        fun hasRunningTimer() = instance?.deleted == false

        const val TICK = "TICK"
        const val FINISH = "FINISH"

        const val TIMER_INDEX = "TIMER_INDEX"
        const val TIMER_LIST = "TIMER_LIST"
        const val TITLE = "TITLE"
        const val REMAINING_TIME = "REMAINING_TIME"
    }
}