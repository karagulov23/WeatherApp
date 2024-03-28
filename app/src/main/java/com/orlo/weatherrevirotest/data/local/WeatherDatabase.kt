package com.orlo.weatherrevirotest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orlo.weatherrevirotest.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherrevirotest.data.local.entity.WeatherDao

@Database(
    entities = [CurrentWeatherEntity::class],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val weatherDao: WeatherDao
}