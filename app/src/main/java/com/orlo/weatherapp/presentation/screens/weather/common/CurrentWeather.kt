package com.orlo.weatherapp.presentation.screens.weather.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orlo.weatherapp.domain.model.current.CurrentWeather
import com.orlo.weatherapp.presentation.ui.theme.Typography
import com.orlo.weatherapp.presentation.ui.util.ImageWithShadow
import com.orlo.weatherapp.presentation.ui.util.timezoneToDate
import com.orlo.weatherapp.util.constants.Const


@Composable
fun CurrentWeather(currentWeather: CurrentWeather) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = currentWeather.main,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = currentWeather.desc,
            fontWeight = FontWeight.SemiBold,
            style = Typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        ImageWithShadow(
            imageUrl = Const.BASE_ICONS_URL + currentWeather.icon + "@4x.png",
            modifier = Modifier.size(200.dp),
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = currentWeather.temp.toInt().toString() + "°C",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = timezoneToDate(currentWeather.timezone),
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}