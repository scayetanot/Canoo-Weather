package com.example.canooweather.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherForeCast(
        @SerializedName("latitude")
        var latitude : Double,
        @SerializedName("longitude")
        var longitude : Double,
        @SerializedName("timezone")
        var timezone: String,
        @SerializedName("currently")
        var currently: CurrentlyEntity,
        @SerializedName("daily")
        var daily: List<DailyEntity>
): Serializable

