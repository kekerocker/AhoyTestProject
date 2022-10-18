package com.dsoft.domain.model

import com.dsoft.domain.extension.convertToFahrenheit
import com.dsoft.domain.extension.shrinkToTwoDigits

data class WeatherForecast(
    val humidity: Double,
    val temperatureCelsius: Double,
    val pressure: Double,
    val date: String
) {
    val temperatureFahrenheit by lazy {
        temperatureCelsius.convertToFahrenheit().shrinkToTwoDigits()
    }

    val humidityString: String by lazy {
        "$humidity%"
    }

    val pressureString: String by lazy {
        "$pressure hPa"
    }
}
