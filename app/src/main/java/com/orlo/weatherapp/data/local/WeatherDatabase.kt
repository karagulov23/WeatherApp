package com.orlo.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orlo.weatherapp.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherapp.data.local.entity.WeatherDao

@Database(
    entities = [CurrentWeatherEntity::class],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val weatherDao: WeatherDao
}