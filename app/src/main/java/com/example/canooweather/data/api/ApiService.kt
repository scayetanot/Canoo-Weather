package com.example.canooweather.data.api

import com.example.canooweather.data.entity.response.WeatherForeCastResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("data/2.5/onecall?lat={latitude}&lon={longitude}&appid={secretid}&exclude=minutely,alerts,hourly")
    suspend fun getForecast(@Path("secretid") secretid: String,
                            @Path("latitude") latitude: Double,
                            @Path("longitude") longitude: Double): WeatherForeCastResponse
}