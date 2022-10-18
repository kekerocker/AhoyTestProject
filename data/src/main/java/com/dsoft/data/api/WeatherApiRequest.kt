package com.dsoft.data.api

import com.dsoft.data.model.WeatherResponse
import com.dsoft.data.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiRequest {
    @GET("data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse


    @GET("https://api.stormglass.io/v2/weather/point")
    suspend fun getDailyForecast(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("params") params: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String,
        @Query("key") apiKey: String
    ): WeatherForecastResponse
}