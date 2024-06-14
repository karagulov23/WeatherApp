package com.orlo.weatherapp.presentation.screens.weather

import com.orlo.weatherapp.domain.model.GeoCode
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.current.CurrentWeather

data class WeatherViewState(
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather = CurrentWeather(),
    val cities: List<GeoCode> = emptyList(),
    var dayOfWeekName: String = "",
    val hourlyWeather: List<HourlyWeather> = emptyList(),
    val addCity: Boolean = false,
    val error: String? = null
)