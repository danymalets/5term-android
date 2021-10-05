package com.example.timer.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import com.example.timer.model.Timer


class TimerService : Service() {

    private lateinit var timerList: List<Timer>
    private var timerIndex = 0

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("timer", "start")
        timerIndex = 0
        timerList = intent?.getParcelableArrayListExtra(TIMER_LIST)!!
        startCountdown(10)
        return START_NOT_STICKY
    }

    private fun startCountdown(seconds: Int){
        object: CountDownTimer(seconds * 1000L, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val sendLevel = Intent()
                sendLevel.action = TICK
                sendLevel.putExtra(TITLE, timerList[timerIndex].title)
                sendLevel.putExtra(DURATION, (((millisUntilFinished + 900) / 1000).toInt()))
                sendBroadcast(sendLevel)
            }

            override fun onFinish() {
                timerIndex++
                if (timerIndex != timerList.size){
                    startCountdown(timerList[timerIndex].duration)
                }
            }
        }.start()
    }

    companion object{
        const val TICK = "TICK"
        const val TIMER_LIST = "TIMER_LIST"
        const val TITLE = "TITLE"
        const val DURATION = "DURATION"
    }
}