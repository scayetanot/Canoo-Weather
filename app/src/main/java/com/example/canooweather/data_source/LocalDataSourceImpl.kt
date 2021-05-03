package com.example.canooweather.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.canooweather.data.entity.ForeCast

@Dao
interface LocalDataSourceImpl {

    @Query("SELECT * FROM ForeCast WHERE city = :city LIMIT 1")
    suspend fun getDailyTemperature(city: String): ForeCast

    @Query("SELECT * FROM ForeCast")
    suspend fun getForecast(): ForeCast

    @Insert(onConflict = REPLACE)
    suspend fun setForecast(foreCast: ForeCast?)

    @Query("DELETE FROM ForeCast")
    suspend fun deleteAllForecast()

}