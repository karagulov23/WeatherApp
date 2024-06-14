package com.orlo.weatherapp.presentation.screens.weather.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.domain.model.current.CurrentWeather
import com.orlo.weatherapp.presentation.ui.util.timezoneToHour
import com.orlo.weatherapp.util.constants.Const

@SuppressLint("RememberReturnType")
@Composable
fun HourlyWeatherDisplay(
    hourlyWeather: HourlyWeather,
    modifier: Modifier = Modifier
) {
    val formattedTime = remember(hourlyWeather) {
        hourlyWeather.time
//        timezoneToHour(hourlyWeather.time)
    }

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp) // здесь задается радиус закругления
            )
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)) // добавляем закругление краев
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = MaterialTheme.colorScheme.onBackground
        )
        AsyncImage(
            modifier = Modifier,
//                .size(64.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(Const.BASE_ICONS_URL + hourlyWeather.icon + "@4x.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = hourlyWeather.weather,
            color = MaterialTheme.colorScheme.onBackground,
        )
        val tempInt = hourlyWeather.temp.toDoubleOrNull()?.toInt() ?: 0
        Text(
            text = "$tempInt°C",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = hourlyWeather.day,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Composable
fun HourlyWeatherPreview() {
//    HourlyWeatherDisplay(hourlyWeather = weathers[0])
}