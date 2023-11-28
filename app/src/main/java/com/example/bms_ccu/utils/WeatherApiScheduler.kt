package com.example.bms_ccu.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bms_ccu.data.network.responses.weather.CityWeather
import com.example.bms_ccu.data.network.responses.weather.WeatherApiResult
import com.example.bms_ccu.data.network.services.WeatherApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class WeatherApiScheduler(val weatherApiService: WeatherApiService) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    val weatherLiveData = MutableLiveData<CityWeather.FilteredDetails>()

    fun scheduleApiCall(longitude: Double, latitude: Double) {
        coroutineScope.launch {
            while(isActive) {
                var retry = true
                while(retry) {
                    when(val result = weatherApiService.getWeatherInfo(longitude, latitude,)) {
                        is WeatherApiResult.Success -> {
                            val weatherInfo = result.data
                            retry = false
                            weatherLiveData.postValue(result.data)
                            Log.d("API_CALL_SUCCESS","$weatherInfo")
                        }
                        is WeatherApiResult.Failure -> {
                            val message = result.message
                            Log.d("API_CALL_FAILURE", "$message")
                            delay(5000)
                        }
                    }
                }
                delay(3600000)
            }
        }
    }
}