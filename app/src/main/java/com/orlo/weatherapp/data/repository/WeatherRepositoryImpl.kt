package com.orlo.weatherapp.data.repository

import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.Either
import com.orlo.weatherapp.data.local.WeatherDatabase
import com.orlo.weatherapp.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherapp.data.mapper.toCurrentWeatherEntity
import com.orlo.weatherapp.data.mapper.toGeoCode
import com.orlo.weatherapp.data.mapper.toHourlyWeather
import com.orlo.weatherapp.data.mapper.toNetworkError
import com.orlo.weatherapp.data.remote.WeatherApi
import com.orlo.weatherapp.domain.model.GeoCode
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.NetworkError
import com.orlo.weatherapp.domain.repository.WeatherRepository
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

    override suspend fun getForecastWeatherByCityName(cityName: String): Either<NetworkError, List<HourlyWeather>> {
        return Either.catch {
            weatherApi.getForecastWeatherByCityName(
                cityName = cityName
            ).toHourlyWeather()
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