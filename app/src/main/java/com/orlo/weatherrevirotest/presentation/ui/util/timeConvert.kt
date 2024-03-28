package com.orlo.weatherrevirotest.presentation.ui.util

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun timezoneToDate(timezone: Int): String {
    val currentDateTime = LocalDateTime.now(ZoneOffset.ofTotalSeconds(timezone))
    val formatter = DateTimeFormatter.ofPattern("EEEE | dd MMMM yyyy")
    return currentDateTime.format(formatter)
}