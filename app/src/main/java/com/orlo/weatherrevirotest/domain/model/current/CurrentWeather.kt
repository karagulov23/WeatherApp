package com.orlo.weatherrevirotest.domain.model.current

data class CurrentWeather(
    val cityName: String = "",
    val main: String = "",
    val desc: String = "",
    val temp: Double = 0.0,
    val timezone: Int = 0,
    val icon: String = "",
//    val weather: List<Weather> = emptyList(),
)
