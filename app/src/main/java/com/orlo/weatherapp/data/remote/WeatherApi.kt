package com.orlo.weatherapp.data.remote

import com.orlo.weatherapp.data.remote.FiveDayForecast.FiveDayForecastResponse
import com.orlo.weatherapp.data.remote.GeoCodeDto.GeoCodeDto
import com.orlo.weatherapp.util.constants.Const.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): CurrentWeatherDto


    @GET("geo/1.0/direct")
    suspend fun getCityByName(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") apiKey: String = API_KEY
    ): GeoCodeDto

    @GET("data/2.5/forecast")
    suspend fun getForecastWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric",
    ): FiveDayForecastResponse

}