package com.example.canooweather.repository

import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyDataEntity
import com.example.canooweather.data.entity.model.ForeCast

interface AppRepository {
    suspend fun getForecast(latitude: Double, longitude: Double): ResultForeCast<ForeCast>
    suspend fun getForecastFromApi(latitude: Double, longitude: Double): ResultForeCast<ForeCast>
    suspend fun getForecastFromDb(): ResultForeCast<ForeCast>

    suspend fun getDetailsForDailyForecast(uuid: String): ResultForeCast<List<DailyDataEntity>>
}