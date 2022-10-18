package com.dsoft.presentation.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.core.util.Resource
import com.dsoft.domain.model.TemperatureType
import com.dsoft.domain.model.Weather
import com.dsoft.domain.usecase.AddFavouriteCityToDBUseCase
import com.dsoft.domain.usecase.GetDailyForecastUseCase
import com.dsoft.domain.usecase.GetWeatherByCityNameUseCase
import com.dsoft.domain.usecase.GetWeatherByUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var getWeatherByUserLocationUseCase: GetWeatherByUserLocationUseCase
    @Inject
    lateinit var getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase
    @Inject
    lateinit var addCityToFavouritesUseCase: AddFavouriteCityToDBUseCase
    @Inject
    lateinit var getDailyForecastUseCase: GetDailyForecastUseCase

    companion object {
        private const val SEARCH_DELAY_MS = 1000L
    }

    private val mWeatherData: MutableLiveData<Resource<Weather>> = MutableLiveData()
    val weatherData: LiveData<Resource<Weather>> get() = mWeatherData

    var currentWeatherItem: Weather? = null
    var currentCoordinates: Weather.Coordinates = Weather.Coordinates(0.0, 0.0)

    private val searchQueryObservable = MutableStateFlow<String?>(null)
    var searchQuery: String
        get() = searchQueryObservable.value.orEmpty()
        set(value) {
            searchQueryObservable.value = value
        }

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQueryObservable
                .debounce(SEARCH_DELAY_MS)
                .collectLatest { text ->
                    text?.let {
                        when (it.isNotBlank()) {
                            true -> {
                                mWeatherData.postValue(Resource.Loading())
                                mWeatherData.postValue(Resource.on { getWeatherByCityNameUseCase(it) })
                            }
                            false -> {
                                val errorText = "Please, enter city name"
                                mWeatherData.postValue(
                                    Resource.Failure(
                                        IllegalArgumentException(
                                            errorText
                                        ), errorText
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }

    fun getWeatherByUserLocation(coordinates: Weather.Coordinates) {
        viewModelScope.launch {
            mWeatherData.postValue(Resource.Loading())
            mWeatherData.postValue(Resource.on { getWeatherByUserLocationUseCase(coordinates) })
        }
    }

    /**
     * Require user subscription (not free)
     */
    fun getDailyForecast(coordinates: Weather.Coordinates) {
        viewModelScope.launch {
            mWeatherData.postValue(Resource.Loading())
            mWeatherData.postValue(Resource.on { getDailyForecastUseCase(coordinates) })
        }
    }

    fun addCityToFavourites(cityName: String) {
        viewModelScope.launch { addCityToFavouritesUseCase(cityName) }
    }
}