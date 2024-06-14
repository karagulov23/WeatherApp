package com.orlo.weatherapp.presentation.screens.weather.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        Alignment.Center
    ) {
        Text(
            text = "Add your city",
            fontSize = 36.sp
        )
    }
}

@Preview
@Composable
fun EmptyScreenPreview() {
    EmptyScreen()
}