package com.example.canooweather.data_source

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.api.ApiService
import com.example.canooweather.data.entity.model.ForeCast
import com.example.canooweather.di.IoDispatcher
import com.example.canooweather.utils.convertToReadableDate
import com.example.canooweather.utils.convertToReadableDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*


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
            val request =
                api.getForecast(BuildConfig.OPEN_WEATHER_SECRET_KEY, latitude, longitude)


            var listOfTodayTemp = request.hourly.data.filter {
                convertToReadableDay(it.time).toInt() == LocalDate.now().dayOfMonth
            }

            ResultForeCast.Success(ForeCast(
                    generateUUID(request.latitude, request.longitude),
                    "",
                    request.latitude,
                    request.longitude,
                    convertToReadableDate(request.daily.data[0].time),
                    request.daily.data[0].summary,
                    request.daily.data[0].icon,
                    request.currently.temperature,
                    request.daily.data[0].temperatureMin,
                    request.daily.data[0].temperatureMax,
                    listOfTodayTemp
            ))
        }

    private fun generateUUID(latitude: Double, longitude: Double): String {
        val uuidBaseString = latitude.toString() + longitude.toString()
        return UUID.nameUUIDFromBytes(uuidBaseString.toByteArray()).toString()
    }
}