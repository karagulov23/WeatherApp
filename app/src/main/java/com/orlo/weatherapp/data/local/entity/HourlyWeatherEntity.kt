package com.orlo.weatherapp.data.local.entity

import androidx.room.Entity

@Entity
data class HourlyWeatherEntity(
    val time: String,
    val temp: String,
    val icon: String
)
