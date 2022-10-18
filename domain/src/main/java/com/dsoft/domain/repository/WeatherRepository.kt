package com.dsoft.domain.repository

import androidx.lifecycle.LiveData
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByUserLocation(coordinates: Weather.Coordinates): Weather
    suspend fun getDailyForecast(coordinates: Weather.Coordinates): Weather
    suspend fun getWeatherByCityName(cityName: String): Weather
    suspend fun addFavouriteCity(cityName: String)
    suspend fun deleteFavouriteCity(favourite: Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
}