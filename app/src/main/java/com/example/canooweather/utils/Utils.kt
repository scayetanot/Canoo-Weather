package com.example.canooweather.utils

import android.net.Uri
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


fun convertToReadableDate(timestamp: Long): String {

    val fullDayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    val timestampAsDateString = DateTimeFormatter.ISO_INSTANT
        .format(java.time.Instant.ofEpochSecond(timestamp))

    val date = LocalDate.parse(timestampAsDateString, fullDayFormat)

    return date.dayOfWeek.toString().substring(0,3)
}

fun convertToReadableHours(timestamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timestamp * 1000
    return DateFormat.format("hh:mm a", cal).toString()
}

fun formatTemperature(temperature: Float?): String {
    return temperature?.toInt().toString() + "\u2109"
}

fun convertToUri(icon: String): Uri = Uri.parse("https://openweathermap.org/img/wn/$icon.png")


