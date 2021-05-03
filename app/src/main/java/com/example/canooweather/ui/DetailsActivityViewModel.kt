package com.example.canooweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.canooweather.data.ResultForeCast
import com.example.canooweather.data.entity.DailyEntity
import com.example.canooweather.repository.AppRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsActivityViewModel @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl
) : ViewModel() {

    private var _resultDailyTemperature = MutableLiveData<List<DailyEntity>>()
    var resultDailyTemperature: LiveData<List<DailyEntity>> = _resultDailyTemperature

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun getDailyTemperatures(city: String?){
        viewModelScope.launch {
            try{
                when(val response = repositoryImpl.getDailyTemperatures(city!!)){
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