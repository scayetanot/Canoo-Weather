package com.example.canooweather.data.entity

import com.google.gson.annotations.SerializedName

data class FeelsLikeEntity (
    @SerializedName("day")
    var day: Float,
    @SerializedName("night")
    var night: Float,
    @SerializedName("eve")
    var eve: Float,
    @SerializedName("morn")
    var morn: Float
)