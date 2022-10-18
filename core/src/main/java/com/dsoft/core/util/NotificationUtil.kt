package com.dsoft.core.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.dsoft.core.receiver.NotificationReceiver
import java.util.*
import javax.inject.Inject

class NotificationUtil @Inject constructor() {
    companion object {
        private const val REQUEST_CODE = 100
        private const val CHANNEL_ID = "101"
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "AHOY_CHANNEL"
            val description = "SOME_AHOY_DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager: NotificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setupNotification(int: Intent, applicationContext: Context) {
        val calendar = Calendar.getInstance()
        val latitude = int.getDoubleExtra("latitude", 0.0)
        val longitude = int.getDoubleExtra("longitude", 0.0)

        calendar.set(Calendar.HOUR_OF_DAY, 6)
        val intent = Intent(applicationContext, NotificationReceiver::class.java).apply {
            putExtra("latitude", latitude)
            putExtra("longitude", longitude)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager =
            applicationContext.getSystemService(Service.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}