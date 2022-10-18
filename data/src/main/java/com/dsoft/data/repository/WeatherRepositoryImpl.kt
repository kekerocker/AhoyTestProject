package com.dsoft.data.repository

import androidx.lifecycle.LiveData
import com.dsoft.core.BuildConfig
import com.dsoft.core.util.DateTimeConst.YEAR_MONTH_DAY
import com.dsoft.data.api.WeatherApiRequest
import com.dsoft.data.db.FavouritesDao
import com.dsoft.data.mapper.toModel
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.model.Weather
import com.dsoft.domain.model.WeatherForecast
import com.dsoft.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiRequest,
    private val favouritesDao: FavouritesDao
) : WeatherRepository {

    private val formatter: SimpleDateFormat by lazy {
        SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault())
    }

    private val apiKey: String by lazy { BuildConfig.WEATHER_API_KEY }
    private val secondApiKey: String by lazy { BuildConfig.SECOND_WEATHER_API_KEY }

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

    override suspend fun getDailyForecast(
        coordinates: Weather.Coordinates
    ): List<WeatherForecast> {
        val params = "airTemperature,pressure,humidity"
        val threeDays = 259200000
        val currentDay = Calendar.getInstance().timeInMillis
        val threeDaysAfter = currentDay + threeDays

        val startDate = formatter.format(currentDay)
        val endDate = formatter.format(threeDaysAfter)
        return api.getDailyForecast(
            coordinates.latitude,
            coordinates.longitude,
            params,
            startDate,
            endDate,
            secondApiKey
        ).toModel()
    }
}