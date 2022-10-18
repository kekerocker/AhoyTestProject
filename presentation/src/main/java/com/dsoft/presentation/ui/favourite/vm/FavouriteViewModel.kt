package com.dsoft.presentation.ui.favourite.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.usecase.DeleteFavouriteFromDBUseCase
import com.dsoft.domain.usecase.GetAllFavouriteCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor() : ViewModel() {
    @Inject lateinit var getAllFavouriteUseCase: GetAllFavouriteCityUseCase
    @Inject lateinit var deleteFavouriteCityUseCase: DeleteFavouriteFromDBUseCase

    val favouriteCities: LiveData<List<Favourite>> get() = getAllFavouriteUseCase()

    fun deleteCityFromFavourites(favourite: Favourite) {
        viewModelScope.launch {
            deleteFavouriteCityUseCase(favourite)
        }
    }
}