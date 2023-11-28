package com.example.bms_ccu.viewModels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bms_ccu.data.network.responses.country_state_city.City
import com.example.bms_ccu.data.network.responses.country_state_city.CityDetails
import com.example.bms_ccu.data.network.responses.country_state_city.CityLocation
import com.example.bms_ccu.data.network.responses.country_state_city.CityLocationResponse
import com.example.bms_ccu.data.network.responses.country_state_city.CityResponse
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.data.network.responses.country_state_city.CountryResponse
import com.example.bms_ccu.data.network.responses.country_state_city.State
import com.example.bms_ccu.data.network.responses.country_state_city.StateResponse
import com.example.bms_ccu.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationSettingsViewModel(private val locationRepository: LocationRepository) : ViewModel() {
    val countryList = MutableLiveData<List<Country>>()
    val filteredCountryList = MediatorLiveData<List<Country>>()

    val stateList = MutableLiveData<List<State>>()
    val filteredStateList = MediatorLiveData<List<State>>()

    val cityList = MutableLiveData<List<City>>()
    val filteredCityList = MediatorLiveData<List<City>>()

    var countryName= MutableLiveData<String>()
    var stateName= MutableLiveData<String>()
    var cityName = MutableLiveData<String>()

    val cityLocation = MutableLiveData<CityLocation>()

    init {
        getAllCountries()

        filteredCountryList.addSource(countryList) {performCountryFilter()}
        filteredCountryList.addSource(countryName) {performCountryFilter()}

        filteredStateList.addSource(stateList) {performStateFilter()}
        filteredStateList.addSource(stateName) {performStateFilter()}

        filteredCityList.addSource(cityList) {performCityFilter()}
        filteredCityList.addSource(cityName) {performCityFilter()}
    }

    fun getAllCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            when(val result = locationRepository.getCountryList()) {
                is CountryResponse.Success -> {
                    countryList.postValue(result.data)
                    stateList.postValue(listOf())
                    cityList.postValue(listOf())
                }
                is CountryResponse.Failure -> {
                    Log.d("COUNTRY EXTRACTION ERROR", "${result.message}")
                }
            }
        }
    }

    fun getStatesByCountry(countryCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val result = locationRepository.getStateList(countryCode)) {
                is StateResponse.Success -> {
                    stateList.postValue(result.data)
                    cityList.postValue(listOf())
                }
                is StateResponse.Failure -> {
                    Log.d("STATE EXTRACTION ERROR", "${result.message}")
                }
            }
        }
    }

    fun getCitiesByCountry(countryCode: String, stateCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val result = locationRepository.getCityList(countryCode, stateCode)) {
                is CityResponse.Success -> {
                    cityList.postValue(result.data)
                }
                is CityResponse.Failure -> {
                    Log.d("CITY EXTRACTION ERROR", "${result.message}")
                }
            }
        }
    }

    fun getCityCoordinates(countryName: String, stateName: String, cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val result = locationRepository.getCityLocation(countryName, stateName, cityName)) {
                is CityLocationResponse.Success -> {
                    cityLocation.postValue(result.data)
                }
                is CityLocationResponse.Failure -> {
                    Log.d("CITY COORIDNATES ERROR", "${result.message}")
                }
            }
        }
    }

    fun performCountryFilter() {
        val countries = countryList.value
        val filterText = countryName?.value ?: ""

        val filterCountries = countries?.filter {
            it.name.lowercase().contains(filterText.lowercase())
        } ?: emptyList()

        filteredCountryList.value = filterCountries
    }

    fun performStateFilter() {
        Log.d("STATE FILTER","Entering to filter")
        val states= stateList.value
        val filterText = stateName?.value ?: ""

        val filterStates = states?.filter {
            it.name.lowercase().contains(filterText.lowercase())
        } ?: emptyList()

        filteredStateList.value = filterStates
    }

    fun performCityFilter() {
        val countries = cityList.value
        val filterText = cityName?.value ?: ""

        val filterCountries = countries?.filter {
            it.name.lowercase().contains(filterText.lowercase())
        } ?: emptyList()

        filteredCityList.value = filterCountries
    }
}