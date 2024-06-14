package com.orlo.weatherapp.data.remote.FiveDayForecast

import com.orlo.weatherapp.data.remote.Coord

data class FiveDayForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherData>,
    val message: Int
)

data class Rain(
    val `3h`: Double
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)


