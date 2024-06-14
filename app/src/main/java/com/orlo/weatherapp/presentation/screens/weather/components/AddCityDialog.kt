package com.orlo.weatherapp.presentation.screens.weather.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.orlo.weatherapp.presentation.screens.weather.WeatherViewModel
import com.orlo.weatherapp.presentation.ui.theme.whitee

@Composable
fun AddCityDialog(
    viewModel: WeatherViewModel,
    fullCityName: String,
    onDismiss: () -> Unit,
    onDismissBottomSheet: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                "Add City",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            Text(
                "Do you want to add $fullCityName to favorites?",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = whitee,
                    contentColor = Color.Black
                ),
                onClick = {
                    onDismiss()
                    onDismissBottomSheet()
                    viewModel.getCurrentWeatherByCityName(fullCityName)
                    viewModel.getForecastWeatherByCityName(fullCityName)
                }
            ) {
                Text("Yes", color = MaterialTheme.colorScheme.onBackground)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = whitee,
                    contentColor = Color.Black
                ),
                onClick = {
                    onDismiss()
                }
            ) {
                Text("No", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    )
}
