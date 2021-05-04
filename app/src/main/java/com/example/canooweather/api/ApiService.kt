package com.example.canooweather.api

import com.example.canooweather.data.entity.response.WeatherForeCast
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/data/2.5/onecall?lat={latitude}&lon={longitude}&appid={secretid}&exclude=minutely,alerts,hourly")
    suspend fun getForecast(@Path("id") id: String,
                            @Path("latitude") latitude: Double,
                            @Path("longitude") longitude: Double): WeatherForeCast
}