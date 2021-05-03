package com.example.canooweather.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.HourlyDataEntity
import com.example.canooweather.repository.AppRepositoryImpl
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DailyTemperatureViewModel @Inject constructor(
        private val repositoryImpl: AppRepositoryImpl
) : ViewModel() {

    private var _resultDailyTemperature = MutableLiveData<List<HourlyDataEntity>>()
    var resultDailyTemperature: LiveData<List<HourlyDataEntity>> = _resultDailyTemperature

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun getHourlyTemperatures(uuid: String?){
        viewModelScope.launch {
            try{
                when(val response = repositoryImpl.getDetailsForHourlyForecast(uuid!!)){
                    is ResultForeCast.Success -> {
                        _resultDailyTemperature.postValue(response.data)
                    }
                    is ResultForeCast.Error -> {
                        _errorMessage.postValue(response.exception.toString())
                    }
                }

            } catch (e: java.lang.Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

}

