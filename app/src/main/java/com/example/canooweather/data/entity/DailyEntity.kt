package com.example.canooweather.data.entity

import com.google.gson.annotations.SerializedName

data class DailyEntity (
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("sunrise")
    var sunrise: Long,
    @SerializedName("sunset")
    var sunset: Long,
    @SerializedName("moonrise")
    var moonrise: Long,
    @SerializedName("moonset")
    var moonset: Long,
    @SerializedName("moon_phase")
    var moonPhase: Float,
    @SerializedName("temp")
    var temp: TempEntity,
    @SerializedName("feels_like")
    var feelsLike: FeelsLikeEntity,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("dew_point")
    var dewPoint: Float,
    @SerializedName("wind_speed")
    var windSpeed: Float,
    @SerializedName("wind_deg")
    var windDeg: Int,
    @SerializedName("wind_gust")
    var windGust: Float,
    @SerializedName("weather")
    var weather: List<WeatherEntity>,
    @SerializedName("clouds")
    var clouds: Int,
    @SerializedName("pop")
    var pop: Float,
    @SerializedName("rain")
    var rain: Float,
    @SerializedName("uvi")
    var uvi: Int
)