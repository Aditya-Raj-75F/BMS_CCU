package com.example.bms_ccu.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bms_ccu.data.network.responses.weather.CityWeather

@Dao
interface LocationDao{
    @Query("SELECT * FROM location LIMIT 1")
    suspend fun getCoordinates() : List<LocationModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoordinates(coordinates: LocationModel)
}