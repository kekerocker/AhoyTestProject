package com.dsoft.domain.usecase

import com.dsoft.domain.model.Weather
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): Weather {
        return repository.getWeatherByCityName(cityName)
    }
}