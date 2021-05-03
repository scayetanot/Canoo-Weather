package com.example.canooweather.data

import androidx.room.TypeConverter
import com.example.canooweather.data.entity.model.ForeCast
import com.example.canooweather.data.entity.HourlyDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class RoomDataConverter : Serializable {

    @TypeConverter
    fun stringFromObject(list: ForeCast?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getObjectFromString(jsonString: String?): ForeCast? {
        val listType: Type = object : TypeToken<ForeCast?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    @TypeConverter
    fun stringFromListObject(list: List<HourlyDataEntity?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getListObjectFromString(jsonString: String?): List<HourlyDataEntity?>? {
        val listType: Type = object : TypeToken<ArrayList<HourlyDataEntity?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}