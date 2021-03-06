package com.example.canooweather.di.modules

import com.example.canooweather.api.ApiService
import com.example.canooweather.data_source.RemoteDataSourceImpl
import com.example.canooweather.di.IoDispatcher
import com.example.canooweather.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        api: ApiService
    ): AppRepositoryImpl {
        val userDataSource = RemoteDataSourceImpl(api, ioDispatcher)
        return AppRepositoryImpl(userDataSource, ioDispatcher)
    }
}