package com.example.canooweather.di.component

import android.content.Context
import com.example.canooweather.di.modules.*
import com.example.canooweather.ui.DetailsActivity
import com.example.canooweather.ui.MainActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        CoroutinesModule::class
    ]
)

interface AppComponents {
    fun context(): Context

    fun retrofit(): Retrofit

    fun inject(mainActivity: MainActivity)
    fun inject(detailsActivity: DetailsActivity)
}