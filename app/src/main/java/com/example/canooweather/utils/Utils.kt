package com.example.canooweather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.format.DateFormat
import java.util.*


fun convertToReadableDate(timestamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timestamp * 1000
    return DateFormat.format("dd-MM-yyyy  hh:mm:ss a", cal).toString()
}

fun convertToReadableDay(timestamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timestamp * 1000
    return DateFormat.format("dd", cal).toString()
}

fun formatTemperature(temperature: Float?): String {
    return temperature?.toInt().toString() + "\u2109"
}

fun findDrawable(context: Context, name: String): Drawable {
    val drawableId = context.resources.getIdentifier(name.replace("-",""), "drawable", context.packageName)
    return context.resources.getDrawable(drawableId)
}


