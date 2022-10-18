package com.dsoft.core.service

import android.app.*
import android.content.Intent
import android.os.IBinder
import com.dsoft.core.util.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService @Inject constructor(): Service() {

    @Inject
    lateinit var notificationUtil: NotificationUtil

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        intent.let(::setupNotification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupNotification(int: Intent) {
        notificationUtil.createNotificationChannel(applicationContext)
        notificationUtil.setupNotification(int, applicationContext)
    }

}