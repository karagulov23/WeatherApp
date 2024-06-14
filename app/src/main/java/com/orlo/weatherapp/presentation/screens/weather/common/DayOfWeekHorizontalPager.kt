package com.orlo.weatherapp.presentation.screens.weather.common

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orlo.weatherapp.domain.model.HourlyWeather
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewModel
import com.orlo.weatherrevirotest.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayHorizontalPager(
    viewModel: WeatherViewModel,
    hourlyWeatherList: List<HourlyWeather>,
) {
    val coroutineScope = rememberCoroutineScope()

    val groupedByDay = hourlyWeatherList.groupBy { it.day }
    val dayNames = groupedByDay.keys.toList()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val pagerState = rememberPagerState(
            pageCount = {
                dayNames.size
            }
        )

        Log.d("401", "dayNames size - ${dayNames.size}")

        IconButton(
            enabled = pagerState.currentPage >= 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 110.dp)
        ) { page ->

            val currentDay = dayNames[page]

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentSize(align = Alignment.Center)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {

//                val currentDay = dayNames[page]
                CarouselItem(day = currentDay)
//                viewModel.getForecastByDayOfWeek(currentDay)
                Log.d("401", "current day $currentDay")

//                viewModel.getForecastByDayOfWeek("Friday")

            }
        }

        IconButton(
            enabled = pagerState.currentPage >= 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CarouselItem(day: String) {
    Column(
        modifier = Modifier.background(color = Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.take(3).uppercase(),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}