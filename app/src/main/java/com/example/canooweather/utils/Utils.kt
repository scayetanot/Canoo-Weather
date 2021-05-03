package com.example.canooweather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.format.DateFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.canooweather.ui.MainActivity.Companion.UUID_KEY
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

fun convertToReadableHour(timestamp: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timestamp * 1000
    return DateFormat.format("hh a", cal).toString()
}

fun formatTemperature(temperature: Float?): String {
    return temperature?.toInt().toString() + "\u2109"
}

fun findDrawable(context: Context, name: String): Drawable {
    val drawableId = context.resources.getIdentifier(name.replace("-",""), "drawable", context.packageName)
    return context.resources.getDrawable(drawableId)
};

fun AppCompatActivity.replaceFragment(fragment: Fragment, container: Int, uuid: String) {
    val className = fragment.javaClass.name

    val bundle = Bundle()
    bundle.putString(UUID_KEY, uuid)
    fragment.arguments = bundle

    supportFragmentManager
        .beginTransaction()
        .replace(container, fragment, className)
        .addToBackStack(className)
        .commit()
}
