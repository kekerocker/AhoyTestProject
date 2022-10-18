package com.dsoft.data.model

data class WeatherForecastResponse(
    val hours: List<HourResponse>?,
    val meta: MetaResponse?
) {
    data class MetaResponse(
        val cost: Int?,
        val dailyQuota: Int?,
        val end: String?,
        val lat: Double?,
        val lng: Double?,
        val params: List<String>?,
        val requestCount: Int?,
        val start: String?
    )
    
    data class HourResponse(
        val airTemperature: AirTemperatureResponse?,
        val humidity: HumidityResponse?,
        val pressure: PressureResponse?,
        val time: String?
    ) {
        data class AirTemperatureResponse(
            val noaa: Double?,
            val sg: Double?
        )

        data class HumidityResponse(
            val noaa: Double?,
            val sg: Double?
        )

        data class PressureResponse(
            val noaa: Double?,
            val sg: Double?
        )
    }
}