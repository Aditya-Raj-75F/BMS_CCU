package com.example.bms_ccu.data.network.responses.country_state_city

data class Country(
    val id : Int,
    val name : String,
    val iso2 : String)

sealed class CountryResponse{
    data class Success(val data: List<Country>) : CountryResponse()
    data class Failure(val message: String) : CountryResponse()
}