package com.example.bms_ccu.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bms_ccu.BmsCcuApplication
import com.example.bms_ccu.repository.LocationRepository
import com.example.bms_ccu.viewModels.LocationSettingsViewModel
import com.example.bms_ccu.viewModels.WeatherWidgetViewModel

class WeatherWidgetViewModelFactory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherWidgetViewModel::class.java)) {
                return  WeatherWidgetViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}

class LocationSettingsViewModelFactory(private val locationRepository: LocationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationSettingsViewModel::class.java)) {
            return LocationSettingsViewModel(locationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}