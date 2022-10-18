package com.dsoft.data.api

import com.dsoft.data.model.WeatherResponse
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


    /**
     * Require subscription
     */
    @GET("data/2.5/forecast/hourly")
    suspend fun getDailyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ): WeatherResponse
}