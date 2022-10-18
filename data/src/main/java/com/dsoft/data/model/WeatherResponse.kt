package com.dsoft.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val base: String?,
    val clouds: CloudsResponse?,
    val cod: Int?,
    @SerializedName("coord") val coordinates: CoordinatesResponse?,
    val dt: Long?,
    val id: Int?,
    val main: MainResponse?,
    val name: String?,
    val sys: SysResponse?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<WeatherInfoResponse>?,
    val wind: WindResponse?
) {
    data class WeatherInfoResponse(
        val description: String?,
        val icon: String?,
        val id: Int?,
        val main: String?
    )

    data class CloudsResponse(
        val all: Int?
    )

    data class CoordinatesResponse(
        val lat: Double?,
        val lon: Double?
    )

    data class MainResponse(
        @SerializedName("feels_like") val feelsLike: Double?,
        @SerializedName("grnd_level") val groundLevel: Int?,
        val humidity: Int?,
        val pressure: Int?,
        @SerializedName("sea_level")val seaLevel: Int?,
        val temp: Double?,
        @SerializedName("temp_max") val temperatureMax: Double?,
        @SerializedName("temp_min") val temperatureMin: Double?
    )

    data class SysResponse(
        val country: String?,
        val id: Int?,
        val sunrise: Int?,
        val sunset: Int?,
        val type: Int?
    )

    data class WindResponse(
        val deg: Int?,
        val gust: Double?,
        val speed: Double?
    )
}
