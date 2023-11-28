package com.example.bms_ccu.data.network.apis

import com.example.bms_ccu.BuildConfig
import com.example.bms_ccu.data.network.responses.weather.CityWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherRetrofitApi {
    @GET("weather")
    suspend fun fetchWeatherDetails(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("units") units : String = "metric",
        @Query("appid") appid : String = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        ) : CityWeather.AllDetails
    companion object {
        operator fun invoke() : WeatherRetrofitApi {
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherRetrofitApi::class.java)
        }
    }
}