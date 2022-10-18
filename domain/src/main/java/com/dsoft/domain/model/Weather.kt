package com.dsoft.domain.model

import com.dsoft.domain.extension.shrinkToTwoDigits

data class Weather(
    val id: Int?,
    val name: String,
    val visibility: Int,
    val coordinates: Coordinates,
    val weather: List<WeatherInfo>,
    val clouds: Int,
    val date: Long,
    val wind: Wind,
    val mainInfo: MainInfo
) {

    data class MainInfo(
        val feelsLike: Double,
        val humidity: Int,
        val pressure: Int,
        val currentTemperature: Double,
        val maxTemp: Double,
        val minTemp: Double
    ) {
        val humidityString: String by lazy {
            "$humidity%"
        }

        val pressureString: String by lazy {
            "$pressure hPa"
        }
        private val minMaxTemperatureCelsius: String by lazy {
            listOf(
                minTemp.shrinkToTwoDigits(),
                maxTemp.shrinkToTwoDigits()
            ).joinToString("-") + "°C"
        }
        private val minMaxTemperatureFahrenheit: String by lazy {
            listOf(
                minTemp.convertToFahrenheit().shrinkToTwoDigits(),
                maxTemp.convertToFahrenheit().shrinkToTwoDigits()
            ).joinToString("-") + "℉"
        }

        fun getCurrentTemperature(type: TemperatureType): String {
            return when (type) {
                TemperatureType.CELSIUS -> "${currentTemperature.shrinkToTwoDigits()}°C"
                TemperatureType.FAHRENHEIT -> currentTemperature.convertToFahrenheit().shrinkToTwoDigits() + "℉"
            }
        }

        fun getFeelsLikeTemperature(type: TemperatureType): String {
            return when (type) {
                TemperatureType.CELSIUS -> "${feelsLike.shrinkToTwoDigits()}°C"
                TemperatureType.FAHRENHEIT -> feelsLike.convertToFahrenheit().shrinkToTwoDigits() + "℉"
            }
        }

        fun getCurrentMinMaxTemperature(type: TemperatureType): String {
            return when (type) {
                TemperatureType.CELSIUS -> minMaxTemperatureCelsius
                TemperatureType.FAHRENHEIT -> minMaxTemperatureFahrenheit
            }
        }

        private fun Double.convertToFahrenheit(): Double {
            return ((this * 9) / 5) + 32
        }
    }

    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    )

    data class WeatherInfo(
        val id: Int,
        val description: String,
        val main: String
    )

    data class Coordinates(
        val latitude: Double,
        val longitude: Double
    ) {
        companion object {
            fun createCoordinates(latitude: Double, longitude: Double): Coordinates {
                return Coordinates(latitude, longitude)
            }
        }
    }
}