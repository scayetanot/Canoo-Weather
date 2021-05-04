package com.example.canooweather.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.canooweather.data.RemoteDataNotFoundException
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.data.entity.ForeCast
import com.example.canooweather.data_source.LocalDataSourceImpl
import com.example.canooweather.data_source.RemoteDataSource
import com.example.canooweather.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.canooweather.utils.InternetUtil
import java.io.IOException
import java.util.*


class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSourceImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    private val isInternetOn = InternetUtil.isInternetOn()

    override suspend fun getForecastFromApi(context: Context, latitude: Double, longitude: Double): ResultForeCast<ForeCast> {
        return when (val result = remoteDataSource.getForecast(latitude, longitude)) {
            is ResultForeCast.Success -> {
                val response = result.data
              //  result.data.city = getCityName(context, result.data.lat, result.data.lon)!!
                withContext(ioDispatcher) {
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

    override suspend fun getForecast(context: Context, latitude: Double, longitude: Double): ResultForeCast<ForeCast> {
        return if (isInternetOn) {
            getForecastFromApi(context, latitude, longitude)
        } else {
            getForecastFromDb()
        }
    }

    override suspend fun getDailyTemperatures(city: String): ResultForeCast<List<DailyEntity>> =
        withContext(ioDispatcher){
            ResultForeCast.Success(localDataSource.getDailyTemperature(city).daily)
        }

    private fun getCityName(ctx: Context, lat: Double, lon: Double): String? {
            val gcd = Geocoder( ctx, Locale.getDefault())
            val addr: List<Address>
            var cityName: String?

            try {
                addr = gcd.getFromLocation(lat, lon, 1)
                cityName = addr.firstOrNull()?.locality
            } catch (e: IOException) {
                cityName = ""
            }
            return cityName!!
    }
}