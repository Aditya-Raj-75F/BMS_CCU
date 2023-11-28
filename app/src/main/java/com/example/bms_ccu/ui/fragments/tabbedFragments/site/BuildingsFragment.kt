package com.example.bms_ccu.ui.fragments.tabbedFragments.site

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bms_ccu.R

/**
 * A simple [Fragment] subclass.
 * Use the [BuildingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BuildingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buildings, container, false)
    }

}