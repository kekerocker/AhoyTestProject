package com.dsoft.data.repository

import androidx.lifecycle.LiveData
import com.dsoft.core.BuildConfig
import com.dsoft.data.api.WeatherApiRequest
import com.dsoft.data.db.FavouritesDao
import com.dsoft.data.mapper.toModel
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.model.Weather
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiRequest,
    private val favouritesDao: FavouritesDao
) : WeatherRepository {

    private val apiKey: String by lazy { BuildConfig.WEATHER_API_KEY }

    override suspend fun getWeatherByUserLocation(coordinates: Weather.Coordinates): Weather =
        api.getWeatherByLocation(coordinates.latitude, coordinates.longitude, apiKey).toModel()

    override suspend fun getWeatherByCityName(cityName: String): Weather =
        api.getWeatherByCityName(cityName, apiKey).toModel()

    override suspend fun addFavouriteCity(cityName: String) {
        favouritesDao.addCity(Favourite(cityName))
    }

    override fun getAllFavourite(): LiveData<List<Favourite>> {
        return favouritesDao.readAllFavourites()
    }

    override suspend fun deleteFavouriteCity(favourite: Favourite) {
        favouritesDao.deleteByCityName(favourite.cityName)
    }

    override suspend fun getDailyForecast(coordinates: Weather.Coordinates): Weather {
        val daysCount = 6
        return api.getDailyForecast(coordinates.latitude, coordinates.longitude, daysCount, apiKey)
            .toModel()
    }
}