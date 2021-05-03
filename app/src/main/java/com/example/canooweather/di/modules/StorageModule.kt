package com.example.canooweather.di.modules

import android.app.Application
import androidx.room.Room
import com.example.canooweather.data_source.LocalDataSource
import com.example.canooweather.data_source.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val application: Application) {

    private var dataSource: LocalDataSource =
        Room.databaseBuilder(application, LocalDataSource::class.java, "db").build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): LocalDataSource {
        return dataSource
    }

    @Singleton
    @Provides
    fun providesAppDao(demoDatabase: LocalDataSource): LocalDataSourceImpl {
        return demoDatabase.localDataObject()
    }
}