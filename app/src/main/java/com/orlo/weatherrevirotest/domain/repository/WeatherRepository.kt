package com.orlo.weatherrevirotest.domain.repository

import arrow.core.Either
import com.orlo.weatherrevirotest.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherrevirotest.domain.model.GeoCode
import com.orlo.weatherrevirotest.domain.model.NetworkError

interface WeatherRepository {

    suspend fun getCurrentWeatherByCityName(cityName: String): Either<NetworkError, CurrentWeatherEntity>

    suspend fun searchByCityName(cityName: String): Either<NetworkError, List<GeoCode>>

    suspend fun deleteCityFromDB(cityName: String)

}