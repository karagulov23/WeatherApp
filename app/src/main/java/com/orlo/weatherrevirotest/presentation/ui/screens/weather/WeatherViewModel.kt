package com.orlo.weatherrevirotest.presentation.ui.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlo.weatherrevirotest.data.local.WeatherDatabase
import com.orlo.weatherrevirotest.data.mapper.toCurrentWeather
import com.orlo.weatherrevirotest.domain.repository.WeatherRepository
import com.orlo.weatherrevirotest.presentation.ui.screens.sendEvent
import com.orlo.weatherrevirotest.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    weatherDatabase: WeatherDatabase
) : ViewModel() {

    private val dao = weatherDatabase.weatherDao

    val cityList: Flow<List<String>> = dao.getAllCities()

    private val _state = MutableStateFlow(WeatherViewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val lastWeather = withContext(Dispatchers.IO) {
                dao.getLastCurrentWeather()
            }
            // Проверка на null, чтобы избежать возможного NPE
            lastWeather?.let { cachingWeatherEntity ->
                _state.update {
                    it.copy(
                        currentWeather = cachingWeatherEntity.toCurrentWeather(),
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun loadCachingWeatherData(cityName: String?) {
        if (cityName != null) {
            dao.getCurrentWeather(cityName)?.collect { currentWeatherEntity ->
                currentWeatherEntity.let { cachingWeatherEntity ->
                    _state.update {
                        it.copy(
                            currentWeather = cachingWeatherEntity.toCurrentWeather(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getCurrentWeatherByCityName(cityName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            weatherRepository.getCurrentWeatherByCityName(cityName)
                .onRight { currentWeather ->
                    dao.upsertCurrentWeather(currentWeather)

                    _state.update {
                        it.copy(
//                            currentWeather = dao.getCurrentWeather(cityName)
                            currentWeather = currentWeather.toCurrentWeather()
                        )
                    }

                }
                .onLeft { error ->
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))

                    loadCachingWeatherData(cityName)

                }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }


    fun deleteCityFromDB(cityName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            weatherRepository.deleteCityFromDB(cityName)
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun searchByCityName(cityName: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            weatherRepository.searchByCityName(cityName)
                .onRight { cities ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        cities = cities
                    )
                }
                .onLeft { error ->
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }

            _state.update {
                it.copy(isLoading = false)
            }

        }
    }
}