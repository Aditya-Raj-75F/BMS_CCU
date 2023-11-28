package com.example.bms_ccu.data.network.services

import com.example.bms_ccu.data.network.apis.OpenStreetMapApi
import com.example.bms_ccu.data.network.responses.country_state_city.CityLocation
import com.example.bms_ccu.data.network.responses.country_state_city.CityLocationResponse

class OpenStreetMapApiService(private val openStreetMapApi: OpenStreetMapApi) {
    suspend fun getCityLocation(country : String, state : String, city : String) : CityLocationResponse {
        val cityLocation : CityLocation
        try {
            val cityDetails  = openStreetMapApi.fetchLocationForCity(city = city,state = state,country = country)
            cityLocation = CityLocation(cityDetails[0].lat, cityDetails[0].lon)
            return CityLocationResponse.Success(cityLocation)
        } catch (exception : Exception) {
            return CityLocationResponse.Failure(exception.message.toString())
        }
    }
}