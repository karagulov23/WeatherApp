package com.orlo.weatherrevirotest.util.constants

class Const {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_ICONS_URL = "https://openweathermap.org/img/wn/"
        const val API_KEY = "ce310f8e35da9ba97b12f5a1c578d344"

    }


}