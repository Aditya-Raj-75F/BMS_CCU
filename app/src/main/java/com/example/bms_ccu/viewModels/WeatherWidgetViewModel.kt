package com.example.bms_ccu.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bms_ccu.BmsCcuApplication
import com.example.bms_ccu.data.network.responses.weather.CityWeather
import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class WeatherView {
    var city : String? = null
    var weather: String? = null
    var icon : String? = null
    var temp : CharSequence? = null
    var temp_min : CharSequence? = null
    var temp_max : CharSequence? = null
    var humidity : String? = null
    var rain : String? = null
    var sunrise : String? = null
    var sunset : String? = null
}

class WeatherWidgetViewModel(context: Context) : ViewModel() {

    val app = context as BmsCcuApplication
    private val weatherApiScheduler = app.scheduler

    var longitude : Double = 84.5
    var latitude : Double = 24.5

    var weatherView = MutableLiveData<WeatherView>()
    var city : String? = "Bengaluru"
//    by Delegates.observable(null) { _, _, _ ->
//        notifyPropertyChanged(BR.cityName)
//    }
    var weather: String? = null
    var icon : String? = null
    var temp : CharSequence? = null
    var temp_min : CharSequence? = null
    var temp_max : CharSequence? = null
    var humidity : String? = null
    var rain : String? = null
    var sunrise : String? = null
    var sunset : String? = null

    // Expose the LiveData to the UI
    val weatherLiveData: MutableLiveData<CityWeather.FilteredDetails>
        get() = weatherApiScheduler.weatherLiveData

    init {
        weatherApiScheduler.scheduleApiCall(longitude, latitude)
    }
//    fun updateWeatherData() {
//        this.city = "Patna"
//        weatherApiScheduler.scheduleApiCall()
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reloadWeatherParameters(weatherApiResponse: CityWeather.FilteredDetails) {
        val weatherParams = WeatherView()
        Log.d("RELOAD_PARAMS","updating vars = $weatherApiResponse")
        weatherParams.city = weatherApiResponse.city
        weatherParams.weather = capitalizeEachWord(weatherApiResponse.weather)
        weatherParams.icon = "_"+weatherApiResponse.icon
        weatherParams.temp = applySuperScript(weatherApiResponse.temp.toString() + "\u00B0"+"c")
        weatherParams.temp_min = applySuperScript(weatherApiResponse.temp_min.toString() + "\u00B0" + "c")
        weatherParams.temp_max = applySuperScript(weatherApiResponse.temp_max.toString() + "\u00B0" + "c")
        weatherParams.humidity = "Humidity: "+weatherApiResponse.humidity.toString()
        weatherParams.rain = "Precipitation: "+weatherApiResponse.rain.toString()
        weatherParams.sunrise = formatLocalDateTime(convertTimeToLocalDateTime(weatherApiResponse.sunrise, weatherApiResponse.timezone))
        weatherParams.sunset = formatLocalDateTime(convertTimeToLocalDateTime(weatherApiResponse.sunset, weatherApiResponse.timezone))
        weatherView.value = weatherParams
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimeToLocalDateTime(time: Long, timezoneOffsetSeconds: Int): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(time),
            ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds))
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDateTime(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        return localDateTime.format(formatter)
    }

    fun capitalizeEachWord(text: String) : String{
        return text.split(" ").joinToString(separator = " ") {
            it.replaceFirstChar {
                it.uppercaseChar()
            }
        }
    }

    fun applySuperScript(text: String) : CharSequence{
        val spannedStringBuilder = SpannableStringBuilder(text)
        spannedStringBuilder.setSpan(SuperscriptSpan(), text.length-1, text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        spannedStringBuilder.setSpan(RelativeSizeSpan(0.6f), text.length-1, text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        return spannedStringBuilder
    }
}