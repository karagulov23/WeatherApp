package com.orlo.weatherrevirotest.data.mapper

import com.orlo.weatherrevirotest.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherrevirotest.data.remote.CurrentWeatherDto
import com.orlo.weatherrevirotest.data.remote.GeoCodeDto.GeoCodeDto
import com.orlo.weatherrevirotest.domain.model.ApiError
import com.orlo.weatherrevirotest.domain.model.GeoCode
import com.orlo.weatherrevirotest.domain.model.NetworkError
import com.orlo.weatherrevirotest.domain.model.current.CurrentWeather
import retrofit2.HttpException
import java.io.IOException

fun CurrentWeatherDto.toCurrentWeatherEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        id = this.id,
        cityName = this.name,
        main = this.weather[0].main,
        icon = this.weather[0].icon,
        desc = this.weather[0].description,
        temp = this.main.temp,
        timezone = this.timezone,
    )
}

fun CurrentWeatherEntity.toCurrentWeather() : CurrentWeather {
    return CurrentWeather(
        cityName = this.cityName,
        main = this.main,
        icon = this.icon,
        desc = this.desc,
        temp = this.temp,
        timezone = this.timezone,
    )
}

fun GeoCodeDto.toGeoCode() : List<GeoCode> {
    return this.map { dtoItem ->
        GeoCode(
            name = dtoItem.name,
            country = dtoItem.country,
            state = dtoItem.state ?: ""
        )
    }
}

fun Throwable.toNetworkError(): NetworkError {
    val error = when(this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error,
        throwable = this
    )
}