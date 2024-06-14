package com.orlo.weatherapp.presentation.screens.weather

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orlo.weatherapp.presentation.screens.weather.common.CarouselWeather
import com.orlo.weatherapp.presentation.screens.weather.common.CurrentWeather
import com.orlo.weatherapp.presentation.screens.weather.common.DayHorizontalPager
import com.orlo.weatherapp.presentation.screens.weather.common.EmptyScreen
import com.orlo.weatherapp.presentation.screens.weather.components.Header
import com.orlo.weatherapp.presentation.screens.weather.components.LoadingDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WeatherScreen(state = state)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun WeatherScreen(
    state: WeatherViewState
) {
    val viewModel: WeatherViewModel = hiltViewModel()
    val isEffectLaunched = remember { mutableStateOf(false) }

    LoadingDialog(isLoading = state.isLoading)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Header(viewModel = viewModel, state = state) }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
        ) {


            if (state.currentWeather.cityName.isNotEmpty() && !isEffectLaunched.value) {
                LaunchedEffect(state.currentWeather.cityName) {
                    viewModel.getForecastWeatherByCityName(state.currentWeather.cityName)
                    isEffectLaunched.value = true
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CurrentWeather(state.currentWeather)
//                  DayHorizontalPager(viewModel, state.hourlyWeather)
                CarouselWeather(state.hourlyWeather)
            }
        }

    }
}
