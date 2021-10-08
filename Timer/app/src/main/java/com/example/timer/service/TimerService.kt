package com.example.timer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.timer.model.Timer


class TimerService : Service() {

    private lateinit var timerList: List<Timer>
    private var timerIndex = 0
    private var completed = false

    override fun onCreate() {
        super.onCreate()
        Log.d("set", "instance")
        instance = this
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("timer", "start")
        timerIndex = 0
        timerList = intent?.getParcelableArrayListExtra(TIMER_LIST)!!
        startCountdown(timerList[0].duration)
        return START_NOT_STICKY
    }

    private fun startCountdown(seconds: Int){
        object: CountDownTimer(seconds * 1000L, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val sendLevel = Intent()
                sendLevel.action = TICK
                sendLevel.putExtra(TITLE, timerList[timerIndex].title)
                sendLevel.putExtra(DURATION, (((millisUntilFinished + 0) / 1000).toInt()))
                sendBroadcast(sendLevel)
            }

            override fun onFinish() {
                timerIndex++
                if (timerIndex == timerList.size){
                    completed = true
                }
                else{
                    startCountdown(timerList[timerIndex].duration)
                }
            }
        }.start()
    }

    companion object{
        private var instance: TimerService? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun startTimerIfNotStarted(context: Context, timerList: ArrayList<Timer>){
            if (!hasRunningTimer()){
                Intent(context, TimerService::class.java).also { intent ->
                    intent.putParcelableArrayListExtra(TIMER_LIST, ArrayList(timerList))
                    context.startForegroundService(intent)
                }
            }
        }

        fun hasRunningTimer() = instance?.completed == false

        const val TICK = "TICK"
        const val TIMER_LIST = "TIMER_LIST"
        const val TITLE = "TITLE"
        const val DURATION = "DURATION"
    }
}