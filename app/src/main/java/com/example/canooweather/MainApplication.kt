package com.example.canooweather

import android.app.Application
import com.example.canooweather.di.component.AppComponents
import com.example.canooweather.di.modules.AppModule
import com.example.canooweather.di.modules.StorageModule
import com.example.canooweather.utils.InternetUtil

open class MainApplication : Application() {

    companion object {
        lateinit var appComponents: AppComponents
    }

    override fun onCreate() {
        super.onCreate()
        appComponents = initDagger(this)
        InternetUtil.init(this)
    }

    private fun initDagger(app: MainApplication): AppComponents =
        DaggerAppComponents.builder()
            .appModule(AppModule(app))
            .storageModule(StorageModule(app))
            .build()
}