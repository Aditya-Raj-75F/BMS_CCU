package com.example.bms_ccu.ui.adapters.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.responses.country_state_city.Country
import com.example.bms_ccu.data.network.responses.country_state_city.State

class StateRecyclerAdapter():
    RecyclerView.Adapter<StateRecyclerAdapter.StateViewHolder>() {

        lateinit var states : List<State>
        var stateSelected = MutableLiveData<State>()

        inner class StateViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val textView = view.findViewById<TextView>(R.id.itemText)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
            return StateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
        }

        override fun getItemCount(): Int {
            return states.size
        }

        override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
            val state = states[position]
            holder.textView.text = state.name
            holder.view.setOnClickListener {
                stateSelected.value = state
            }
        }

        fun setStateData(stateData : List<State>) {
            this.states = stateData
        }
}