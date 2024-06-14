package com.orlo.weatherapp.domain.model.current

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
