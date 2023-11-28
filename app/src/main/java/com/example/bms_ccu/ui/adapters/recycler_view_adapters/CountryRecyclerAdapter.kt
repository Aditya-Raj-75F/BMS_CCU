package com.example.bms_ccu.ui.adapters.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.viewModels.LocationSettingsViewModel

class CountryRecyclerAdapter() :
    RecyclerView.Adapter<CountryRecyclerAdapter.CountryViewHolder>() {

    lateinit var countries : List<Country>
    var countrySelected = MutableLiveData<Country>()

    inner class CountryViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView>(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.textView.text = country.name
        holder.view.setOnClickListener {
            countrySelected.value = country
        }
    }

    fun setCountryData(countryData : List<Country>) {
        this.countries = countryData
    }
}