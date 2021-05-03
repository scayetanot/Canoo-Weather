package com.example.canooweather.data

sealed class ResultForeCast<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultForeCast<T>()
    data class Error(val exception: Exception) : ResultForeCast<Nothing>()
}