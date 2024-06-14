package com.orlo.weatherapp.util.constants

class Const {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        const val BASE_URL = "https://api.openweathermap.org/"
        const val BASE_ICONS_URL = "https://openweathermap.org/img/wn/"
        const val API_KEY = "d1ae773ff89b9bc35482c80809f4bbff"

    }


}