package com.dsoft.core.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.core.app.NotificationCompat
import com.dsoft.core.R
import com.dsoft.domain.model.TemperatureType
import com.dsoft.domain.model.Weather
import com.dsoft.domain.usecase.GetWeatherByUserLocationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver @Inject constructor() : HiltBroadcastReceiver() {

    @Inject
    lateinit var getCurrentWeather: GetWeatherByUserLocationUseCase

    private val NOTIFICATION_CHANNEL_ID = "101"

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val coordinates = Weather.Coordinates(latitude, longitude)

        CoroutineScope(IO).launch {
            val async = async { getCurrentWeather(coordinates) }
            val weather = async.await()
            val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Weather info")
                .setContentText("Current temperature in ${weather.name}: ${weather.mainInfo.getCurrentTemperature(TemperatureType.CELSIUS)}")
                .setSmallIcon(R.drawable.ic_cloud)
                .setAutoCancel(true)

            notificationManager.notify(10, builder.build())
        }
    }

}

/**
 * This class required for Hilt to inject dependencies.
 */
abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {}
}