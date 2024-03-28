package com.orlo.weatherrevirotest.presentation.ui.screens.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orlo.weatherrevirotest.R
import com.orlo.weatherrevirotest.presentation.ui.screens.weather.chart.ForecastChart
import com.orlo.weatherrevirotest.presentation.ui.util.components.Header
import com.orlo.weatherrevirotest.presentation.ui.util.components.LoadingDialog

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WeatherScreen(state = state)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherScreen(
    state: WeatherViewState
) {
    val viewModel: WeatherViewModel = hiltViewModel()

    LoadingDialog(isLoading = state.isLoading)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.thunder),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Header(viewModel, state)
                CurrentWeather(state.currentWeather)
                Carousel(carouselItems)
                TemperatureGraph()
            }
        }
    }
}
