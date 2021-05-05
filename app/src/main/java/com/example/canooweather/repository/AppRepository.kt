package com.example.canooweather.repository

import android.content.Context
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.data.entity.ForeCast

interface AppRepository {
    suspend fun getForecast(context: Context, latitude: Double, longitude: Double): ResultForeCast<ForeCast>
    suspend fun getForecastFromApi(context: Context, latitude: Double, longitude: Double): ResultForeCast<ForeCast>
    suspend fun getForecastFromDb(): ResultForeCast<ForeCast>

    suspend fun getDayWeather(city: String): ResultForeCast<List<DailyEntity>>
}