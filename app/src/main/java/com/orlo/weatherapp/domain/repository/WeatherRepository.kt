package com.orlo.weatherapp.domain.repository

import arrow.core.Either
import com.orlo.weatherapp.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherapp.domain.model.GeoCode
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.NetworkError
import java.time.DayOfWeek

interface WeatherRepository {

    suspend fun getCurrentWeatherByCityName(cityName: String): Either<NetworkError, CurrentWeatherEntity>

    suspend fun searchByCityName(cityName: String): Either<NetworkError, List<GeoCode>>

    suspend fun deleteCityFromDB(cityName: String)

    suspend fun getForecastWeatherByCityName(cityName: String): Either<NetworkError, List<HourlyWeather>>

}