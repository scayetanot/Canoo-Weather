package com.example.canooweather.data.entity.response

import com.example.canooweather.data.entity.CurrentlyEntity
import com.example.canooweather.data.entity.DailyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherForeCast(
        @SerializedName("lat")
        var lat : Double,
        @SerializedName("lon")
        var lon : Double,
        @SerializedName("timezone")
        var timezone: String,
        @SerializedName("timezone_offset")
        var timezoneOffset: Int,
        @SerializedName("currently")
        var currently: CurrentlyEntity,
        @SerializedName("daily")
        var daily: List<DailyEntity>
): Serializable

