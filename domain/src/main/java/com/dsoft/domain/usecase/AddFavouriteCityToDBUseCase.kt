package com.dsoft.domain.usecase

import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class AddFavouriteCityToDBUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String) {
        repository.addFavouriteCity(cityName)
    }
}