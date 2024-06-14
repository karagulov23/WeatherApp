package com.orlo.weatherapp.data.remote.GeoCodeDto

data class GeoCodeDtoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String? = ""
)