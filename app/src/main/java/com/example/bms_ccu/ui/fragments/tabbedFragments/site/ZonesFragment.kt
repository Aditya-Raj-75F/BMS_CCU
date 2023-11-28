package com.example.bms_ccu.ui.fragments.tabbedFragments.site

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.bms_ccu.R
import com.example.bms_ccu.databinding.FragmentZonesBinding
import com.example.bms_ccu.utils.WeatherWidgetViewModelFactory
import com.example.bms_ccu.viewModels.WeatherWidgetViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ZonesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZonesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentZonesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_zones, container, false
        )
        val weatherWidgetViewModel : WeatherWidgetViewModel by viewModels {
            WeatherWidgetViewModelFactory(
                this.requireContext().applicationContext
            )
        }
        binding.viewModel = weatherWidgetViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        weatherWidgetViewModel.weatherLiveData.observeForever(Observer {
                weatherResult ->
            weatherWidgetViewModel.reloadWeatherParameters(weatherResult)
        })
        return binding.root
    }

    companion object {
        @BindingAdapter("bindImageDrawable")
        @JvmStatic
        fun bindImageDrawable(imageView: ImageView, drawableName: String?) {
            if (!drawableName.isNullOrEmpty()) {
                val drawableId = imageView.context.resources.getIdentifier(
                    drawableName,
                    "drawable",
                    imageView.context.packageName
                )
                if (drawableId != 0) {
                    imageView.setImageResource(drawableId)
                }
            }
        }
    }
}