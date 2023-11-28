package com.example.bms_ccu.data.network.apis

import androidx.lifecycle.LiveData
import com.example.bms_ccu.BuildConfig
import com.example.bms_ccu.data.network.responses.country_state_city.City
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.data.network.responses.country_state_city.State
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryStateCityApi {
    @GET("countries")
    suspend fun fetchCountryList(
        @Header("X-CSCAPI-KEY") apiKey: String = BuildConfig.COUNTRY_STATE_CITY_API_KEY,
    ): List<Country>

    @GET("countries/{ciso}/states")
    suspend fun fetchStatesByCountry(
        @Path("ciso") countryCode: String,
        @Header("X-CSCAPI-KEY") apiKey: String = BuildConfig.COUNTRY_STATE_CITY_API_KEY
    ): List<State>

    @GET("countries/{ciso}/states/{siso}/cities")
    suspend fun fetchCityByStateAndCountry(
        @Path("ciso") countryCode: String,
        @Path("siso") stateCode: String,
        @Header("X-CSCAPI-KEY") apiKey: String = BuildConfig.COUNTRY_STATE_CITY_API_KEY
    ): List<City>

    companion object {
        operator fun invoke() : CountryStateCityApi {
            return Retrofit.Builder()
                .baseUrl("https://api.countrystatecity.in/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryStateCityApi::class.java)
        }
    }
}