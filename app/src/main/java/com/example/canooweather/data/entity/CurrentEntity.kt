package com.example.canooweather.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrentEntity (
        @SerializedName("dt")
        var dt: Long,
        @SerializedName("sunrise")
        var sunrise: Long,
        @SerializedName("sunset")
        var sunset: Long,
        @SerializedName("temp")
        var temp: Float,
        @SerializedName("feels_like")
        var feelsLike: Float,
        @SerializedName("pressure")
        var pressure: Int,
        @SerializedName("humidity")
        var humidity: Int,
        @SerializedName("dew_point")
        var dewPoint: Float,
        @SerializedName("uvi")
        var uvi: Int,
        @SerializedName("clouds")
        var clouds: Int,
        @SerializedName("visibility")
        var visibility: Int,
        @SerializedName("wind_speed")
        var windSpeed: Float,
        @SerializedName("wind_deg")
        var windDeg: Int,
        @SerializedName("weather")
        var weather: List<WeatherEntity>
) : Serializable
