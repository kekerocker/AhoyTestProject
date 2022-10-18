package com.dsoft.domain.usecase

import com.dsoft.domain.model.Weather
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(coordinates: Weather.Coordinates): Weather {
        return repository.getDailyForecast(coordinates)
    }
}