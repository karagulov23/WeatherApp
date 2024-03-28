package com.orlo.weatherrevirotest.data.local.entity

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Upsert
    suspend fun upsertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Query("DELETE FROM CurrentWeatherEntity WHERE cityName = :cityName")
    suspend fun deleteCityFromDB(cityName: String)

    @Query("SELECT * FROM CurrentWeatherEntity WHERE cityName = :cityName")
    fun getCurrentWeather(cityName: String): Flow<CurrentWeatherEntity>?

    @Query("SELECT DISTINCT cityName FROM CurrentWeatherEntity")
    fun getAllCities(): Flow<List<String>>


}