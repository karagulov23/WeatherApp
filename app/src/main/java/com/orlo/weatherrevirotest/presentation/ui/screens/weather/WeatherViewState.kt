package com.orlo.weatherrevirotest.presentation.ui.screens.weather

import com.orlo.weatherrevirotest.domain.model.GeoCode
import com.orlo.weatherrevirotest.domain.model.current.CurrentWeather

data class WeatherViewState(
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather = CurrentWeather(),
    val cities: List<GeoCode> = emptyList(),
    val addCity: Boolean = false,
    val error: String? = null
)
