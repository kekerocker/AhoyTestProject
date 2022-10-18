package com.dsoft.data.mapper

import com.dsoft.data.model.WeatherResponse
import com.dsoft.domain.extension.kelvinToCelsius
import com.dsoft.domain.model.Weather

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