package com.example.bms_ccu.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.apis.WeatherRetrofitApi
import com.example.bms_ccu.ui.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)

        viewPager.adapter = adapter
        val tabNames = listOf(R.string.zones, R.string.systems, R.string.buildings, R.string.alerts)
        TabLayoutMediator(tabLayout, viewPager) {
            tab, position ->
            tab.text = getString(tabNames[position])
        }.attach()
        Log.d("USER_TEST","Logging Incoming")
        GlobalScope.launch {
            Log.d("USER_TEST", "${ WeatherRetrofitApi.invoke().fetchWeatherDetails(24.75, 84.35) }")
        }
    }

    fun toggleDrawer(view: View) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            drawerLayout.openDrawer(GravityCompat.START, true)
        }
    }

}