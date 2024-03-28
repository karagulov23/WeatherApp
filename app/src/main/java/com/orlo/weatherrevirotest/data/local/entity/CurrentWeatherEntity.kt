package com.orlo.weatherrevirotest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeatherEntity(
    @PrimaryKey
    val id: Int = 0,
    val cityName: String = "",
    val main: String = "",
    val desc: String = "",
    val temp: Double = 0.0,
    val timezone: Int = 0,
    val icon: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

