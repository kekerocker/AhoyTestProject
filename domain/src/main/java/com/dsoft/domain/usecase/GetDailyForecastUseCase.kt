package com.dsoft.domain.usecase

import com.dsoft.domain.model.Weather
import com.dsoft.domain.model.WeatherForecast
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(coordinates: Weather.Coordinates): List<WeatherForecast> {
        return repository.getDailyForecast(coordinates)
    }
}