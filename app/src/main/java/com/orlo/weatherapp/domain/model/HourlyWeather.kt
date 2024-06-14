package com.orlo.weatherapp.domain.model

data class HourlyWeather(
    val weather: String,
    val day: String,
    val time: String,
    val icon: String,
    val temp: String
)
