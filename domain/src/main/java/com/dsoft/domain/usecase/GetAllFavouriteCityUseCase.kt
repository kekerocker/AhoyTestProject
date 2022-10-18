package com.dsoft.domain.usecase

import androidx.lifecycle.LiveData
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.repository.WeatherRepository
import javax.inject.Inject

class GetAllFavouriteCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(): LiveData<List<Favourite>> {
        return repository.getAllFavourite()
    }
}