package com.orlo.weatherapp.presentation.screens.weather.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.current.CurrentWeather


@Composable
fun CarouselWeather(hourlyWeather: List<HourlyWeather>) {

    LazyRow(content = {
        items(hourlyWeather.size) { weatherData ->
            HourlyWeatherDisplay(
                hourlyWeather = hourlyWeather[weatherData],
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
    })

}

@Preview
@Composable()
fun CarouselWeatherPreview() {
//    CarouselWeather(weathers)
}
