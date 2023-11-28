package com.example.bms_ccu.data.network.apis

import com.example.bms_ccu.data.network.responses.country_state_city.CityDetails
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenStreetMapApi {

    @GET("search")
    suspend fun fetchLocationForCity(
        @Query("format") format: String = "json",
        @Query("city") city : String,
        @Query("state") state : String,
        @Query("country") country: String
    ) : List<CityDetails>

    companion object {
        operator fun invoke() : OpenStreetMapApi {
            return Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenStreetMapApi::class.java)
        }
    }
}