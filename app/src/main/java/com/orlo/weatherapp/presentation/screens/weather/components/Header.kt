package com.orlo.weatherapp.presentation.screens.weather.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewModel
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewState
import com.orlo.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.orlo.weatherapp.R

@Composable
fun Header(
    viewModel: WeatherViewModel,
    state: WeatherViewState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        LocationCard(viewModel, state)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LocationCard(
    viewModel: WeatherViewModel,
    state: WeatherViewState,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val cityList by viewModel.cityList.collectAsState(initial = emptyList())

    if (isBottomSheetVisible) {
        LocationSearchBottomSheet(viewModel, state, onDismiss = { isBottomSheetVisible = false })
        isMenuExpanded = false
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            isMenuExpanded = !isMenuExpanded
        }
    ) {
        Icon(
            Icons.Default.LocationOn,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null
        )
//        Spacer(modifier = Modifier.padding(6.dp))
        Text(
            text = state.currentWeather.cityName,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onBackground
        )
//        Spacer(modifier = Modifier.padding(6.dp))
        IconWithRotation(isMenuExpanded)
    }

    Spacer(modifier = Modifier.height(8.dp))

    DropdownMenu(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        expanded = isMenuExpanded,
        properties = PopupProperties(focusable = true),
        onDismissRequest = { isMenuExpanded = false }
    )
    {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent)
            ) {
                Row(Modifier.clickable {
                    isBottomSheetVisible = true
                }) {
                    Icon(
                        Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null
                    )
                    Text(
                        text = "Search",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                cityList.forEach { city ->
                    CityItem(
                        city = city,
                        onCitySelected = { selectedCity ->
                            viewModel.getCurrentWeatherByCityName(selectedCity)
                            viewModel.getForecastWeatherByCityName(selectedCity)
                            viewModel.getForecastByDayOfWeek("Saturday")
                            isMenuExpanded = false
                        },
                        onDeleteClicked = { cityToDelete ->
                            viewModel.deleteCityFromDB(cityToDelete)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CityItem(city: String, onCitySelected: (String) -> Unit, onDeleteClicked: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .clickable {
                    onCitySelected(city)
                },
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clickable { expanded = true }
        )
        DropdownMenu(
            modifier = Modifier.padding(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            Text(
                modifier = Modifier
                    .clickable {
                        onDeleteClicked(city)
                    },
                text = "Delete",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }

}

@Composable
fun IconWithRotation(isExpanded: Boolean) {
    val rotationDegrees by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )
    Icon(
        painter = painterResource(id = R.drawable.ic_dropdown),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = null,
        modifier = Modifier
            .padding(6.dp)
            .rotate(rotationDegrees)
    )
}

@Preview()
@Composable
fun HeaderPreview() {
    WeatherAppTheme() {
        Box(Modifier.background(Color.Black)) {
//            Header(cityName = "New-York")
        }
    }
}
