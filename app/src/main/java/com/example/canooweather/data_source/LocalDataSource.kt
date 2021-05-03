package com.example.canooweather.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.canooweather.data.RoomDataConverter
import com.example.canooweather.data.entity.ForeCast

@Database(entities = [ForeCast::class], version = 1)
@TypeConverters(RoomDataConverter::class)
abstract class LocalDataSource : RoomDatabase() {

    abstract fun localDataObject(): LocalDataSourceImpl

    companion object {
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getDatabase(context: Context): LocalDataSource {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, LocalDataSource::class.java, "weather_db")
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { INSTANCE = it }
            }
        }
    }
}