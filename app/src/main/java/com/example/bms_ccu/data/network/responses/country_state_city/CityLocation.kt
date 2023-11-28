package com.example.bms_ccu.data.network.responses.country_state_city

data class CityLocation(
    val latitude : String,
    val longitude : String
)

data class CityDetails(
    val addresstype : String,
    val boundingbox : Array<String>,
    val `class` : String,
    val display_name : String,
    val importance : Double,
    val lat : String,
    val license : String,
    val lon : String,
    val name : String,
    val osm_id : Long,
    val osm_type : String,
    val place_id : Long,
    val place_rank : Int,
    val type : String
)

sealed class CityLocationResponse {
    data class Success(val data : CityLocation) : CityLocationResponse()
    data class Failure(val message : String) : CityLocationResponse()
}