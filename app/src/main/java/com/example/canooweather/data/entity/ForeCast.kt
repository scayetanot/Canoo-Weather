package com.example.canooweather.data.entity

import androidx.room.Entity

@Entity(tableName = "ForeCast", primaryKeys = ["city"])
data class ForeCast (
    var city: String,
    var lat: Double,
    var lon: Double,
    var current: CurrentlyEntity,
    var daily: List<DailyEntity>
)
