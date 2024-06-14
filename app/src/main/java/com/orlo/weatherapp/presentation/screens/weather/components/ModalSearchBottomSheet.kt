package com.orlo.weatherapp.presentation.screens.weather.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewModel
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewState
import com.orlo.weatherapp.presentation.ui.theme.whitee


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchBottomSheet(
    viewModel: WeatherViewModel,
    state: WeatherViewState,
    onDismiss: () -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    var addCity by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("") }

    if (addCity) {
        AddCityDialog(
            viewModel,
            selectedCity,
            onDismiss = { addCity = !addCity },
            onDismissBottomSheet = onDismiss
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = whitee,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = RoundedCornerShape(8.dp),
                value = searchText,
                onValueChange = { searchText = it },
                label = {
                    Text(
                        "Enter city", color = MaterialTheme.colorScheme.onBackground
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = whitee,
                    contentColor = Color.Black
                ),
                onClick = {
                    viewModel.searchByCityName(searchText)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search", color = MaterialTheme.colorScheme.onBackground)
            }

            LazyColumn(
                contentPadding = PaddingValues(8.dp),
            ) {
                items(state.cities) { city ->
                    val textToShow = "${city.name}, ${city.country}, ${city.state}"
                    val textToAdd = "${city.name}, ${city.country}"
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                addCity = true
                                selectedCity = textToAdd
                            },
                        text = textToShow,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

