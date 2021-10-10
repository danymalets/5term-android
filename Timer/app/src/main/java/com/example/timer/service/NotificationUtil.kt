package com.example.timer.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import com.example.timer.MainActivity
import com.example.timer.R
import com.example.timer.fragment.TimerFragment

class NotificationUtil {
    private lateinit var builder: Builder
    private lateinit var context: Context

    private var receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == TimerService.TICK) {
                val title = intent.getStringExtra(TimerService.TITLE)
                val duration = intent.getIntExtra(TimerService.DURATION, 0)
                setNewText(context, duration.toString())
            }
        }
    }

    fun startNotification(context: Context){
        this.context = context
        createNotificationChannel(context)
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

//            val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
//                .setComponentName(MainActivity::class.java)
//                .setGraph(R.navigation.nav_graph)
//                .setDestination(R.id.destination)
//                .setArguments(bundle)
//                .createPendingIntent()

        builder = Builder(context, Companion.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentTitle("Hi")
            .setContentText("Hello")
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder!!.build())
        }

        context.registerReceiver(receiver, IntentFilter(TimerService.TICK))
    }

    fun stopNotification(context: Context){
        context.applicationContext.unregisterReceiver(receiver)
    }

    fun setNewText(context: Context?, text: String){
        if (context != null) {
            builder.setContentTitle(text)
            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "timer channel"
            val descriptionText = "timer channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Companion.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "timer"
    }
}