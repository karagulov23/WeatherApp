package com.orlo.weatherrevirotest.presentation.ui.screens.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.orlo.weatherrevirotest.R
import com.orlo.weatherrevirotest.domain.model.current.CurrentWeather
import com.orlo.weatherrevirotest.presentation.ui.screens.weather.chart.ForecastChart
import com.orlo.weatherrevirotest.presentation.ui.theme.Typography
import com.orlo.weatherrevirotest.presentation.ui.theme.WeatherReviroTestTheme
import com.orlo.weatherrevirotest.presentation.ui.util.timezoneToDate
import com.orlo.weatherrevirotest.util.constants.Const.Companion.BASE_ICONS_URL
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun WeatherSc(
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.thunder),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            ForecastChart()
        }
    }
}

@Composable
fun CurrentWeather(currentWeather: CurrentWeather) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Устанавливаем вертикальное расположение вверху

    ) {
        Text(
            text = currentWeather.main,
            fontWeight = FontWeight.SemiBold,
            style = Typography.bodyMedium
        )
        Text(
            text = currentWeather.desc,
            fontWeight = FontWeight.SemiBold,
            style = Typography.labelMedium
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(BASE_ICONS_URL + currentWeather.icon + "@4x.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(200.dp),
//            error = painterResource(id = R.drawable.ic_error)
        )
        Text(
            text = currentWeather.temp.toInt().toString() + "°C",
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = timezoneToDate(currentWeather.timezone))
    }
}

data class CarouselItemData(val text: String, val iconResourceId: Int)

val carouselItems = listOf(
    CarouselItemData("SUN", R.drawable.ic_rainy),
    CarouselItemData("TUE", R.drawable.ic_rainy),
    CarouselItemData("THU", R.drawable.ic_rainy),
    CarouselItemData("SUT", R.drawable.ic_rainy),
    CarouselItemData("MON", R.drawable.ic_rainy),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    carouselItems: List<CarouselItemData>,
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val pagerState = rememberPagerState(
            pageCount = {
                5
            }, initialPage = 2
        )

        IconButton(
            enabled = pagerState.currentPage >= 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = null
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 110.dp)
        ) { page ->
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
                    contentColor = Color.White
                )
            ) {
                CarouselItem(carouselItem = carouselItems[page])
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
                tint = Color.White,
                contentDescription = null
            )
        }
    }

}

@Composable
fun CarouselItem(carouselItem: CarouselItemData) {
    Column(
        modifier = Modifier
            .background(color = Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = carouselItem.text)
        Icon(
            painter = painterResource(id = carouselItem.iconResourceId),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
@Preview
fun WeatherScreenPreview() {
    WeatherReviroTestTheme {
        WeatherSc()
    }
}

