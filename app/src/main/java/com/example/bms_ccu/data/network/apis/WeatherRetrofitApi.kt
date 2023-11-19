package com.example.bms_ccu.data.network.apis

import com.example.bms_ccu.BuildConfig
import com.example.bms_ccu.data.network.responses.CityWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRetrofitApi {
    @GET("weather")
    suspend fun fetchWeatherDetails(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("units") units : String = "metrics",
        @Query("appid") appid : String = BuildConfig.API_KEY
        ) : CityWeather
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