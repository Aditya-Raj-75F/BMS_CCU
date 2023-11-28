package com.example.bms_ccu

import android.app.Application
import com.example.bms_ccu.data.database.LocationDao
import com.example.bms_ccu.data.database.LocationDatabase
import com.example.bms_ccu.data.network.apis.CountryStateCityApi
import com.example.bms_ccu.data.network.apis.OpenStreetMapApi
import com.example.bms_ccu.data.network.apis.WeatherRetrofitApi
import com.example.bms_ccu.data.network.services.CountryStateCityApiService
import com.example.bms_ccu.data.network.services.OpenStreetMapApiService
import com.example.bms_ccu.data.network.services.WeatherApiService
import com.example.bms_ccu.repository.LocationRepository
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.CityRecyclerAdapter
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.CountryRecyclerAdapter
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.StateRecyclerAdapter
import com.example.bms_ccu.utils.WeatherApiScheduler


class BmsCcuApplication : Application() {
    lateinit var weatherApiService: WeatherApiService
    lateinit var countryStateCityApiService: CountryStateCityApiService
    lateinit var openStreetMapApiService: OpenStreetMapApiService
    lateinit var scheduler: WeatherApiScheduler
    lateinit var locationRepository: LocationRepository
    lateinit var countryAdapter: CountryRecyclerAdapter
    lateinit var stateAdapter: StateRecyclerAdapter
    lateinit var cityAdapter: CityRecyclerAdapter

    override fun onCreate() {
        super.onCreate()
        val weatherRetrofitApi : WeatherRetrofitApi = WeatherRetrofitApi.invoke()
        val countryStateCityApi : CountryStateCityApi = CountryStateCityApi.invoke()
        val openStreetMapApi : OpenStreetMapApi = OpenStreetMapApi.invoke()
        val locationDatabase : LocationDatabase = LocationDatabase(applicationContext)
        val locationDao : LocationDao = locationDatabase.getLocationDao()
        weatherApiService= WeatherApiService(weatherRetrofitApi)
        countryStateCityApiService = CountryStateCityApiService(countryStateCityApi)
        openStreetMapApiService = OpenStreetMapApiService(openStreetMapApi)
        scheduler = WeatherApiScheduler(weatherApiService)
        locationRepository = LocationRepository(openStreetMapApiService, countryStateCityApiService, locationDao)
        countryAdapter = CountryRecyclerAdapter()
        stateAdapter = StateRecyclerAdapter()
        cityAdapter = CityRecyclerAdapter()
//        val weatherLiveData: MutableLiveData<CityWeather.FilteredDetails> = scheduler.weatherLiveData
//
//            weatherLiveData.observeForever(Observer {
//                    weatherResult ->
//                Log.d("Live_Data_Changed","$weatherResult")
//            })
//        // Start the scheduler
//        scheduler.scheduleApiCall()
    }
}