package com.example.timer.fragment.timer.adapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavArgs
import androidx.navigation.NavDeepLinkBuilder
import com.example.timer.MainActivity
import com.example.timer.R
import com.example.timer.fragment.timer.TimerFragmentArgs
import com.example.timer.model.Sequence
import com.example.timer.service.TimerService

class NotificationAdapter {
    private var builder: Builder? = null

    private var has: Boolean = false

    private lateinit var mService: TimerService
    private var mBound: Boolean = false

    private var context: Context? = null

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            mService = binder.getService()
            setNewText(context, mService.getTitle() + " - " + mService.getRemainingTime())
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private var receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == TimerService.TICK) {
                val title = intent.getStringExtra(TimerService.TITLE)
                val duration = intent.getIntExtra(TimerService.DURATION, 0)
                setNewText(context, duration.toString())
            }
        }
    }

    fun startNotification(sequence: Sequence, context: Context){
        this.context = context
        val intent = Intent(context, TimerService::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

        has = true

        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.timerFragment)
            .setArguments(
                Bundle().also {
                    it.putParcelable("sequence", sequence)
                    it.putBoolean("firstInit", false)
                }
            )
            .createPendingIntent()

        builder = Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_timer_24)
            .setAutoCancel(true)
            .setContentTitle("Timer")
            .setContentText("Wait...")
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder!!.build())
        context.registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    fun tryStopNotification(context: Context){
        if (!has) return
        has = false

        NotificationManagerCompat.from(context).cancel(1)
        context.applicationContext.unregisterReceiver(receiver)
    }

    fun setNewText(context: Context?, text: String){
        if (context != null && builder != null && has) {
            builder!!.setContentTitle(text)
            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder!!.build())
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Timer channel"
            val descriptionText = "Timer channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "timer"
        private const val NOTIFICATION_ID = 1
    }
}