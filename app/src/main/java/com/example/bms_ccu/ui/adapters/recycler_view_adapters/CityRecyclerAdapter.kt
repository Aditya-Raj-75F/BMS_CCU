package com.example.bms_ccu.ui.adapters.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.responses.country_state_city.City

class CityRecyclerAdapter () :
    RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder>() {

    lateinit var cities : List<City>
    var citySelected = MutableLiveData<City>()

    inner class CityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView>(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.textView.text =city.name
        holder.view.setOnClickListener {
            citySelected.value = city
        }
    }

    fun setCityData(cityData : List<City>) {
        this.cities = cityData
    }
}