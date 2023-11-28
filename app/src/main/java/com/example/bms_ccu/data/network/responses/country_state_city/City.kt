package com.example.bms_ccu.data.network.responses.country_state_city

data class City(
    val id : Int,
    val name : String
)

sealed class CityResponse {
    data class Success(val data: List<City>) : CityResponse()
    data class Failure(val message: String) : CityResponse()
}
