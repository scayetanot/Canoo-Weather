package com.example.canooweather.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.canooweather.data.RemoteDataNotFoundException
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.data.entity.ForeCast
import com.example.canooweather.data_source.RemoteDataSource
import com.example.canooweather.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.io.IOException
import java.util.*


class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    private var cache: ForeCast? = null

    override suspend fun getForecast(context: Context, latitude: Double, longitude: Double): ResultForeCast<ForeCast> {
        return when (val result = remoteDataSource.getForecast(latitude, longitude)) {
            is ResultForeCast.Success -> {
                val response = result.data.also { cache = it }
                response.city = getCityName(context, result.data.lat, result.data.lon)!!
                ResultForeCast.Success(response)
            }
            is ResultForeCast.Error -> {
                ResultForeCast.Error(RemoteDataNotFoundException())
            }
        }
    }

    override suspend fun getDailyForeCast(): ResultForeCast<List<DailyEntity>> {
        return if (cache == null) {
            ResultForeCast.Error(RemoteDataNotFoundException())
        } else {
            ResultForeCast.Success(cache!!.daily)
        }
    }

    private fun getCityName(ctx: Context, lat: Double, lon: Double): String? {
            val gcd = Geocoder( ctx, Locale.getDefault())
            val addr: List<Address>
            var cityName: String? = ""

            try {
                addr = gcd.getFromLocation(lat, lon, 1)
                cityName = if(addr.firstOrNull()?.locality.isNullOrEmpty()) addr.firstOrNull()?.adminArea else addr.firstOrNull()?.locality
            } catch (e: IOException) {
                cityName = ""
            }
            return cityName
    }
}