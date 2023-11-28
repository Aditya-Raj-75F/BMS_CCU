package com.example.bms_ccu.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "location")
data class LocationModel(val latitude: Double, val longitude: Double) : Serializable {
    @PrimaryKey
    var id: Int = 1
}