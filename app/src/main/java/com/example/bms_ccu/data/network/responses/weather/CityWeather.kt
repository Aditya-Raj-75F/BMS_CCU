package com.example.bms_ccu.data.network.responses.weather

import com.google.gson.annotations.SerializedName

sealed class CityWeather {

    data class FilteredDetails(
        val city : String,
        val weather: String,
        val icon : String,
        val temp : Double,
        val temp_min : Double,
        val temp_max : Double,
        val humidity : Int,
        val rain : Double?,
        val sunrise : Long,
        val sunset : Long,
        val timezone: Int
    ) : CityWeather()
    data class AllDetails(
        val coord : Coordinates,
        val weather : List<Weather>,
        val base : String,
        val main : BasicMetricDetails,
        val visibility : Int,
        val wind : WindDetails,
        val clouds : CloudDetails,
        val rain : RainDetails?,
        val snow : SnowDetails?,
        val dt : Long,
        val sys : CountryTimings,
        val timezone : Int,
        val id : Long,
        val name : String,
        val cod : Int
    )  : CityWeather()

    data class Coordinates(
        val lon : Double,
        val lat : Double
    )

    data class Weather(
        val id : Int,
        val main : String,
        val description : String,
        val icon : String
    )

    data class BasicMetricDetails(
        val temp : Double,
        val feels_like : Double,
        val pressure : Int,
        val humidity : Int,
        val temp_min : Double,
        val temp_max : Double,
        val sea_level : Int,
        val grnd_level : Int
    )

    data class WindDetails(
        val speed : Double,
        val deg : Int,
        val gust : Double
    )

    data class RainDetails(
        @SerializedName("1h") val oneHour : Double?,
        @SerializedName("3h") val threeHour : Double?
    )

    data class SnowDetails(
        @SerializedName("1h") val oneHour : Double?,
        @SerializedName("3h") val threeHour : Double?
    )

    data class CloudDetails(
        val all : Int
    )

    data class CountryTimings(
        val country : String,
        val sunrise : Long,
        val sunset : Long
    )
}
