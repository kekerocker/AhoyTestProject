package com.dsoft.domain.usecase

import com.dsoft.domain.model.Favourite
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteFavouriteFromDBUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: Favourite) {
        repository.deleteFavouriteCity(cityName)
    }
}