package com.example.bms_ccu.data.network.services

import com.example.bms_ccu.data.network.apis.WeatherRetrofitApi
import com.example.bms_ccu.data.network.responses.weather.CityWeather
import com.example.bms_ccu.data.network.responses.weather.WeatherApiResult

class WeatherApiService(private val weatherRetrofitApi: WeatherRetrofitApi) {
    suspend fun getWeatherInfo(longitude: Double, latitude: Double) : WeatherApiResult {
        val weatherDetails: CityWeather.AllDetails
        try {
            weatherDetails = weatherRetrofitApi.fetchWeatherDetails(latitude , longitude)
            val filteredDetails = CityWeather.FilteredDetails(
                weatherDetails.name,
                weatherDetails.weather[0].description,
                weatherDetails.weather[0].icon,
                weatherDetails.main.temp,
                weatherDetails.main.temp_min,
                weatherDetails.main.temp_max,
                weatherDetails.main.humidity,
                weatherDetails.rain?.oneHour?: weatherDetails.rain?.threeHour ?: null,
                weatherDetails.sys.sunrise,
                weatherDetails.sys.sunset,
                weatherDetails.timezone
            )
            return WeatherApiResult.Success(filteredDetails)
        } catch (e: Exception) {
            return WeatherApiResult.Failure(e.message.toString())
        }
    }
}