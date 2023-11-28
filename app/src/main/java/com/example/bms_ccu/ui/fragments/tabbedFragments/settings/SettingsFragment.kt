package com.example.bms_ccu.ui.fragments.tabbedFragments.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bms_ccu.BmsCcuApplication
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.apis.CountryStateCityApi
import com.example.bms_ccu.data.network.responses.country_state_city.City
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.data.network.responses.country_state_city.State
import com.example.bms_ccu.databinding.FragmentSettingsBinding
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.CityRecyclerAdapter
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.CountryRecyclerAdapter
import com.example.bms_ccu.ui.adapters.recycler_view_adapters.StateRecyclerAdapter
import com.example.bms_ccu.utils.LocationSettingsViewModelFactory
import com.example.bms_ccu.viewModels.LocationSettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    lateinit var  application: BmsCcuApplication
    lateinit var binding: FragmentSettingsBinding

    lateinit var countryAdapter: CountryRecyclerAdapter
    lateinit var stateAdapter: StateRecyclerAdapter
    lateinit var cityAdapter: CityRecyclerAdapter
    lateinit var locationSettingsViewModel: LocationSettingsViewModel
    var country: Country? = null
    var state: State? = null
    var city: City? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        application = (requireContext().applicationContext as BmsCcuApplication)
        val locationRepository = application.locationRepository
        locationSettingsViewModel = ViewModelProvider(this, LocationSettingsViewModelFactory(locationRepository)).get(LocationSettingsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.viewmodel = locationSettingsViewModel
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryAdapter = application.countryAdapter
        stateAdapter = application.stateAdapter
        cityAdapter = application.cityAdapter

        binding.countryList.layoutManager = LinearLayoutManager(view.context)
        binding.stateList.layoutManager = LinearLayoutManager(view.context)
        binding.cityList.layoutManager = LinearLayoutManager(view.context)

        locationSettingsViewModel.filteredCountryList.observe(viewLifecycleOwner) {
            countryAdapter.setCountryData(it)
            binding.countryList.adapter = countryAdapter
        }
        countryAdapter.countrySelected.observe(viewLifecycleOwner) {
            binding.editCountry.setText(it.name)
            binding.editState.setText("")
            binding.editCity.setText("")
            locationSettingsViewModel.getStatesByCountry(it.iso2)
            this.country = it
            locationSettingsViewModel.filteredStateList.observe(viewLifecycleOwner) {
                stateAdapter.setStateData(it)
                binding.stateList.adapter = stateAdapter
            }
        }
        stateAdapter.stateSelected.observe(viewLifecycleOwner) {
            if(country!=null) {
                binding.editState.setText(it.name)
                binding.editCity.setText("")
                this.state = it
                locationSettingsViewModel.getCitiesByCountry(country!!.iso2, it.iso2)
                locationSettingsViewModel.filteredCityList.observe(viewLifecycleOwner) {
                    cityAdapter.setCityData(it)
                    binding.cityList.adapter = cityAdapter
                }
            }
            else {
                binding.editState.setText("")
                locationSettingsViewModel.stateList.value= listOf()
            }
        }

        cityAdapter.citySelected.observe(viewLifecycleOwner) {
            if(country != null && state != null) {
                binding.editCity.setText(it.name)
                this.city = it
                locationSettingsViewModel.getCityCoordinates(country!!.name, state!!.name, it.name)
            }
            else {
                binding.editCity.setText("")
                locationSettingsViewModel.cityList.value = listOf()
            }
        }

    }

    override fun onResume() {
//        super.onResume()
        val locationRepository = application.locationRepository
        binding.viewmodel = ViewModelProvider(this, LocationSettingsViewModelFactory(locationRepository)).get(LocationSettingsViewModel::class.java)
        countryAdapter = application.countryAdapter
        stateAdapter = application.stateAdapter
        cityAdapter = application.cityAdapter
        country = null
        state = null
        city = null
        binding.editCity.setText("")
        binding.editState.setText("")
        binding.editCountry.setText("")
        super.onResume()
    }
}