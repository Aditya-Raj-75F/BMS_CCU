package com.example.bms_ccu.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.bms_ccu.R
import com.example.bms_ccu.data.network.apis.CountryStateCityApi
import com.example.bms_ccu.data.network.apis.OpenStreetMapApi
import com.example.bms_ccu.ui.adapters.tab_layout_adapters.SettingsViewPagerAdapter
import com.example.bms_ccu.ui.adapters.tab_layout_adapters.ViewPagerAdapter
import com.example.bms_ccu.viewModels.WeatherWidgetViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var viewModel: WeatherWidgetViewModel

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        window.decorView.setOnApplyWindowInsetsListener {view, windowInsets ->
            if(windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                || windowInsets.isVisible(WindowInsetsCompat.Type.systemBars())) {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }
            view.onApplyWindowInsets(windowInsets)
        }

        viewModel = WeatherWidgetViewModel(applicationContext)
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
        val configTabLayout = findViewById<TabLayout>(R.id.settingOrProfile)
        val activity = this
        var adapter : FragmentStateAdapter = ViewPagerAdapter(activity)
        var tabNames = listOf(R.string.zones, R.string.systems, R.string.buildings, R.string.alerts)


        configTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position!=0) {
                    adapter = SettingsViewPagerAdapter(activity)
                    tabNames = listOf(R.string.floor_layout, R.string.settings, R.string.tuners)
                }
                else {
                    adapter = ViewPagerAdapter(activity)
                    tabNames = listOf(R.string.zones, R.string.systems, R.string.buildings, R.string.alerts)
                }
                viewPager.adapter = adapter
                TabLayoutMediator(tabLayout, viewPager) {
                        tab, position ->
                    tab.text = getString(tabNames[position])
                }.attach()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) {
                tab, position ->
            tab.text = getString(tabNames[position])
        }.attach()

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("USER_TEST", "${OpenStreetMapApi.invoke().fetchLocationForCity("json", "Aurangabad", "Bihar", "India")}")
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