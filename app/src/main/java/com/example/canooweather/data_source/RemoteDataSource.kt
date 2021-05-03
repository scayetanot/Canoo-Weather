package com.example.canooweather.data_source

import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.model.ForeCast

interface RemoteDataSource {
    suspend fun getForecast(latitude: Double, longitude: Double): ResultForeCast<ForeCast>
}