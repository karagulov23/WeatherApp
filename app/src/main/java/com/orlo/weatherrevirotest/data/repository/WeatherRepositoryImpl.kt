package com.orlo.weatherrevirotest.data.repository

import arrow.core.Either
import com.orlo.weatherrevirotest.data.local.WeatherDatabase
import com.orlo.weatherrevirotest.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherrevirotest.data.mapper.toCurrentWeatherEntity
import com.orlo.weatherrevirotest.data.mapper.toGeoCode
import com.orlo.weatherrevirotest.data.mapper.toNetworkError
import com.orlo.weatherrevirotest.data.remote.WeatherApi
import com.orlo.weatherrevirotest.domain.model.GeoCode
import com.orlo.weatherrevirotest.domain.model.NetworkError
import com.orlo.weatherrevirotest.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    weatherDatabase: WeatherDatabase
) : WeatherRepository {
    private val dao = weatherDatabase.weatherDao

    override suspend fun getCurrentWeatherByCityName(cityName: String): Either<NetworkError, CurrentWeatherEntity> {
        return Either.catch {
            weatherApi.getCurrentWeatherByCityName(
                cityName = cityName
            ).toCurrentWeatherEntity()
        }.mapLeft { it.toNetworkError() }
    }

    override suspend fun searchByCityName(cityName: String): Either<NetworkError, List<GeoCode>> {
        return Either.catch {
        weatherApi.getCityByName(cityName).toGeoCode()
        }.mapLeft { it.toNetworkError() }
    }

    override suspend fun deleteCityFromDB(cityName: String) {
        dao.deleteCityFromDB(cityName)
    }

}