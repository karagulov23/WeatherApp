package com.orlo.weatherapp.data.mapper

import com.orlo.weatherapp.data.local.entity.CurrentWeatherEntity
import com.orlo.weatherapp.data.remote.CurrentWeatherDto
import com.orlo.weatherapp.data.remote.FiveDayForecast.FiveDayForecastResponse
import com.orlo.weatherapp.data.remote.GeoCodeDto.GeoCodeDto
import com.orlo.weatherapp.domain.model.ApiError
import com.orlo.weatherapp.domain.model.GeoCode
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.NetworkError
import com.orlo.weatherapp.domain.model.current.CurrentWeather
import com.orlo.weatherapp.presentation.ui.util.timezontToDayTime
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

fun CurrentWeatherEntity.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = this.cityName,
        main = this.main,
        icon = this.icon,
        desc = this.desc,
        temp = this.temp,
        timezone = this.timezone,
    )
}

fun GeoCodeDto.toGeoCode(): List<GeoCode> {
    return this.map { dtoItem ->
        GeoCode(
            name = dtoItem.name,
            country = dtoItem.country,
            state = dtoItem.state ?: ""
        )
    }
}

fun Throwable.toNetworkError(): NetworkError {
    val error = when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error,
        throwable = this
    )
}

fun FiveDayForecastResponse.toHourlyWeather(): List<HourlyWeather> {
    return this.list.map { weatherData ->

//        val dateTime = weatherData.dt_txt.split(" ")
        val weather = weatherData.weather[0].main
        val (day, time) = timezontToDayTime(weatherData.dt.toLong())
        val icon = weatherData.weather.firstOrNull()?.icon ?: ""
        val temp = weatherData.main.temp.toString()

        HourlyWeather(
            weather = weather,
            day = day,
            time = time,
            icon = icon,
            temp = temp
        )

    }
}
