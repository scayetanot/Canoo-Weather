package com.example.canooweather.data

import androidx.room.TypeConverter
import com.example.canooweather.data.entity.CurrentlyEntity
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.data.entity.ForeCast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type
import kotlin.collections.ArrayList

class RoomDataConverter : Serializable {

    @TypeConverter
    fun stringFromObject(list: CurrentlyEntity?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getObjectFromString(jsonString: String?): CurrentlyEntity? {
        val listType: Type = object : TypeToken<ForeCast?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    @TypeConverter
    fun stringFromListObject(list: List<DailyEntity?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getListObjectFromString(jsonString: String?): List<DailyEntity?>? {
        val listType: Type = object : TypeToken<ArrayList<DailyEntity?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}