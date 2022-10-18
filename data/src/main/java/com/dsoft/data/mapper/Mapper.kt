package com.dsoft.data.mapper

import com.dsoft.core.util.DateTimeConst.DAY_MONTH_YEAR
import com.dsoft.core.util.DateTimeConst.SOME_FORMAT
import com.dsoft.data.model.WeatherForecastResponse
import com.dsoft.data.model.WeatherResponse
import com.dsoft.domain.extension.kelvinToCelsius
import com.dsoft.domain.model.Weather
import com.dsoft.domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*

fun WeatherResponse.toModel(): Weather {
    return Weather(
        id = id ?: 0,
        name = name ?: "",
        visibility = visibility ?: 0,
        date = dt ?: 0L,
        weather = weather?.map {
            Weather.WeatherInfo(
                id = it.id ?: 0,
                description = it.description ?: "",
                main = it.main ?: ""
            )
        } ?: emptyList(),
        coordinates = Weather.Coordinates.createCoordinates(
            coordinates?.lat ?: 0.0,
            coordinates?.lon ?: 0.0
        ),
        clouds = clouds?.all ?: 0,
        wind = Weather.Wind(
            deg = wind?.deg ?: 0,
            gust = wind?.gust ?: 0.0,
            speed = wind?.speed ?: 0.0
        ),
        mainInfo = Weather.MainInfo(
            feelsLike = main?.feelsLike?.kelvinToCelsius() ?: 0.0,
            humidity = main?.humidity ?: 0,
            currentTemperature = main?.temp?.kelvinToCelsius() ?: 0.0,
            pressure = main?.pressure ?: 0,
            maxTemp = main?.temperatureMax?.kelvinToCelsius() ?: 0.0,
            minTemp = main?.temperatureMin?.kelvinToCelsius() ?: 0.0
        )
    )
}

fun WeatherForecastResponse.toModel(): List<WeatherForecast> {
    return this.hours?.map { response ->
        WeatherForecast(
            humidity = response.humidity?.sg ?: 0.0,
            temperatureCelsius = response.airTemperature?.sg ?: 0.0,
            pressure = response.pressure?.sg ?: 0.0,
            date = response.time?.formatToDate() ?: ""
        )
    } ?: emptyList()
}

private fun String.formatToDate(): String {
    val inputFormatter = SimpleDateFormat(SOME_FORMAT, Locale.getDefault())
    val outputFormatter = SimpleDateFormat(DAY_MONTH_YEAR, Locale.getDefault())
    val date = inputFormatter.parse(this)?.time ?: 0L
    return outputFormatter.format(date)
}