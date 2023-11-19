package com.example.bms_ccu.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bms_ccu.ui.fragments.tabbedFragments.AlertsFragments
import com.example.bms_ccu.ui.fragments.tabbedFragments.BuildingsFragment
import com.example.bms_ccu.ui.fragments.tabbedFragments.SystemsFragment
import com.example.bms_ccu.ui.fragments.tabbedFragments.ZonesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ZonesFragment()
            1 -> SystemsFragment()
            2 -> BuildingsFragment()
            3 -> AlertsFragments()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

}