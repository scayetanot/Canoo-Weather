package com.example.canooweather.repository

import com.example.canooweather.data.RemoteDataNotFoundException
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyDataEntity
import com.example.canooweather.data.entity.model.ForeCast
import com.example.canooweather.data.entity.HourlyDataEntity
import com.example.canooweather.data_source.LocalDataSourceImpl
import com.example.canooweather.data_source.RemoteDataSource
import com.example.canooweather.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.canooweather.utils.InternetUtil
import java.util.*


class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSourceImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    private val isInternetOn = InternetUtil.isInternetOn()

    override suspend fun getForecastFromApi(latitude: Double, longitude: Double): ResultForeCast<ForeCast> {
        return when (val result = remoteDataSource.getForecast(latitude, longitude)) {
            is ResultForeCast.Success -> {
                val response = result.data
                withContext(ioDispatcher) {
                    // for the purpose of the App, all entities are deleted
                    //localDataSource.deleteAllForecast()
                    localDataSource.setForecast(response)
                }
                ResultForeCast.Success(response)
            }
            is ResultForeCast.Error -> {
                ResultForeCast.Error(RemoteDataNotFoundException())
            }
        }
    }

    override suspend fun getForecastFromDb(): ResultForeCast<ForeCast> =
        withContext(ioDispatcher) {
            ResultForeCast.Success(localDataSource.getForecast())
        }

    override suspend fun getForecast(latitude: Double, longitude: Double): ResultForeCast<ForeCast> {
        return if (isInternetOn) {
            getForecastFromApi(latitude, longitude)
        } else {
            getForecastFromDb()
        }
    }

    override suspend fun getDetailsForDailyForecast(uuid: String): ResultForeCast<List<DailyDataEntity>> =
        withContext(ioDispatcher){
            ResultForeCast.Success(localDataSource.getDailyTemperature(uuid).)
        }
}