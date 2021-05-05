package com.example.canooweather.data.entity

data class ForeCast (
    var city: String,
    var lat: Double,
    var lon: Double,
    var current: CurrentEntity,
    var daily: List<DailyEntity>
)
