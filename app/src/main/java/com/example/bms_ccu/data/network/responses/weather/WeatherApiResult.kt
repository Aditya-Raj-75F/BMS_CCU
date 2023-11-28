package com.example.bms_ccu.data.network.responses.weather

sealed class WeatherApiResult {
    data class Success(val data: CityWeather.FilteredDetails) : WeatherApiResult()
    data class Failure(val message: String) : WeatherApiResult()
}