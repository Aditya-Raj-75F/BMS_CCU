package com.example.bms_ccu.data.network.responses.country_state_city

data class State(
    val id : Int,
    val name : String,
    val iso2 : String
)

sealed class StateResponse{
    data class Success(val data: List<State>) : StateResponse()
    data class Failure(val message: String) : StateResponse()
}
