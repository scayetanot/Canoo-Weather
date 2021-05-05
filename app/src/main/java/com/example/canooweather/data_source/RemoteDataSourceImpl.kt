package com.example.canooweather.data_source


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.canooweather.BuildConfig
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.api.ApiService
import com.example.canooweather.data.RemoteDataNotFoundException
import com.example.canooweather.data.entity.ForeCast
import com.example.canooweather.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

class RemoteDataSourceImpl(
    private val api: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): ResultForeCast<ForeCast> =
        withContext(ioDispatcher) {
            val request = api.getForecast(BuildConfig.OPEN_WEATHER_SECRET_KEY, latitude, longitude)

            ResultForeCast.Success(ForeCast(
                "",
                request.lat,
                request.lon,
                request.current,
                request.daily))
        }
    }
