package com.orlo.weatherrevirotest.presentation.ui.util.components

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.orlo.weatherrevirotest.presentation.ui.screens.weather.WeatherViewModel
import com.orlo.weatherrevirotest.presentation.ui.theme.whitee

@Composable
fun AddCityDialog(
    viewModel: WeatherViewModel,
    fullCityName: String,
    onDismiss: () -> Unit,
    onDismissBottomSheet: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Add City", fontSize = 16.sp) },
        text = { Text("Do you want to add $fullCityName to favorites?", fontSize = 16.sp) },
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
                }
            ) {
                Text("Yes")
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
                Text("No")
            }
        }
    )
}
