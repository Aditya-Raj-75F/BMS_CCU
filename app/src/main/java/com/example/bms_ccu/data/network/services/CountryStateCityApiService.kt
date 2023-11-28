package com.example.bms_ccu.data.network.services

import com.example.bms_ccu.data.network.apis.CountryStateCityApi
import com.example.bms_ccu.data.network.responses.country_state_city.City
import com.example.bms_ccu.data.network.responses.country_state_city.CityResponse
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.data.network.responses.country_state_city.CountryResponse
import com.example.bms_ccu.data.network.responses.country_state_city.State
import com.example.bms_ccu.data.network.responses.country_state_city.StateResponse

class CountryStateCityApiService(private val countryStateCityApi : CountryStateCityApi) {
    suspend fun getCountryList() : CountryResponse {
        val countryList : List<Country>
        try {
            countryList = countryStateCityApi.fetchCountryList()
            return CountryResponse.Success(countryList)
        } catch (exception : Exception) {
            return CountryResponse.Failure(exception.message.toString())
        }
    }

    suspend fun getStateList(countryCode : String) : StateResponse {
        val stateList : List<State>
        try {
            stateList = countryStateCityApi.fetchStatesByCountry(countryCode)
            return StateResponse.Success(stateList)
        } catch (exception :Exception) {
            return StateResponse.Failure(exception.message.toString())
        }
    }

    suspend fun getCityList(countryCode: String, stateCode: String) : CityResponse {
        val cityList : List<City>
        try {
            cityList = countryStateCityApi.fetchCityByStateAndCountry(countryCode, stateCode)
            return CityResponse.Success(cityList)
        }catch (exception : Exception) {
            return CityResponse.Failure(exception.message.toString())
        }
    }
}