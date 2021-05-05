package com.example.canooweather.api

import com.example.canooweather.data.entity.response.WeatherForeCast
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/onecall?&exclude=minutely,alerts,hourly")
    suspend fun getForecast(@Query("appid") id: String,
                            @Query("lat") latitude: Double,
                            @Query("lon") longitude: Double): WeatherForeCast
}