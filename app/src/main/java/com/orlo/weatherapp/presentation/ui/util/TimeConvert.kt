package com.orlo.weatherapp.presentation.ui.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun timezoneToDate(timezone: Int): String {
    val currentDateTime = LocalDateTime.now(ZoneOffset.ofTotalSeconds(timezone))
    val formatter = DateTimeFormatter.ofPattern("EEEE | dd MMMM yyyy")
    return currentDateTime.format(formatter)
}

fun timezoneToHour(timezone: Int): String {
    val currentDateTime = LocalDateTime.now(ZoneOffset.ofTotalSeconds(timezone))
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return currentDateTime.format(formatter)
}

fun timezontToDayTime(timestamp: Long): Pair<String, String> {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
    val day = dateTime.dayOfWeek.name.lowercase().capitalize()
    val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    return Pair(day, time)
}
