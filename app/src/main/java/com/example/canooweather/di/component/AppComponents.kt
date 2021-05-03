package com.example.canooweather.di.component

import android.content.Context
import com.example.canooweather.data_source.LocalDataSource
import com.example.canooweather.data_source.LocalDataSourceImpl
import com.example.canooweather.di.modules.*
import com.example.canooweather.ui.MainActivity
import com.example.canooweather.ui.fragment.DailyTemperaturesFragment
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        CoroutinesModule::class,
        StorageModule::class
    ]
)

interface AppComponents {
    fun context(): Context

    fun retrofit(): Retrofit

    fun appDataObject(): LocalDataSourceImpl

    fun appDataBase(): LocalDataSource

    fun inject(mainActivity: MainActivity)
    fun inject(hourlyTemperaturesFragment: DailyTemperaturesFragment)
}