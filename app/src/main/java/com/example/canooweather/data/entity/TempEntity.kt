package com.example.canooweather.data.entity

import com.google.gson.annotations.SerializedName

data class TempEntity(
    @SerializedName("day")
    var day: Float,
    @SerializedName("min")
    var min: Float,
    @SerializedName("max")
    var max: Float,
    @SerializedName("night")
    var night: Float,
    @SerializedName("eve")
    var eve: Float,
    @SerializedName("morn")
    var morn: Float
)