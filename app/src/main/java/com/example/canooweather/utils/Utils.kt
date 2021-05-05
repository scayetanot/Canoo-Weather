package com.example.canooweather.utils

import android.net.Uri
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun convertToReadableDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd-mm-yyyy")
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timestamp * 1000
    //val date: LocalDate = LocalDate.parse(formatter.format(cal.time))
    return "TODAY" //date.dayOfWeek.toString()
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


