package com.orlo.weatherapp.data.remote.FiveDayForecast

import com.orlo.weatherapp.data.remote.Clouds
import com.orlo.weatherapp.data.remote.Main
import com.orlo.weatherapp.data.remote.Sys
import com.orlo.weatherapp.data.remote.Weather
import com.orlo.weatherapp.data.remote.Wind

data class WeatherData(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)