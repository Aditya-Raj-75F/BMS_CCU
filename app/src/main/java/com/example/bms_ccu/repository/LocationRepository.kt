package com.example.bms_ccu.repository

import com.example.bms_ccu.data.database.LocationDao
import com.example.bms_ccu.data.database.LocationModel
import com.example.bms_ccu.data.network.responses.country_state_city.CityLocationResponse
import com.example.bms_ccu.data.network.responses.country_state_city.CityResponse
import com.example.bms_ccu.data.network.responses.country_state_city.CountryResponse
import com.example.bms_ccu.data.network.responses.country_state_city.StateResponse
import com.example.bms_ccu.data.network.services.CountryStateCityApiService
import com.example.bms_ccu.data.network.services.OpenStreetMapApiService

class LocationRepository(
    private val openStreetApiService : OpenStreetMapApiService,
    private val countryStateCityApiService: CountryStateCityApiService,
    private val locationDao: LocationDao
) {
    suspend fun getCountryList() : CountryResponse {
       return countryStateCityApiService.getCountryList()
    }

    suspend fun getStateList(countryCode : String) : StateResponse {
        return countryStateCityApiService.getStateList(countryCode)
    }

    suspend fun getCityList(countryCode : String, stateCode : String) : CityResponse {
        return countryStateCityApiService.getCityList(countryCode, stateCode)
    }

    suspend fun getCityLocation(country : String, state : String, city : String) : CityLocationResponse {
        return openStreetApiService.getCityLocation(country, state, city)
    }

    suspend fun addCityLocation(latitude: Double, longitude: Double) {
        locationDao.addCoordinates(LocationModel(latitude, longitude))
    }

    suspend fun getCityLocation() : LocationModel{
        return locationDao.getCoordinates()[0]
    }
}