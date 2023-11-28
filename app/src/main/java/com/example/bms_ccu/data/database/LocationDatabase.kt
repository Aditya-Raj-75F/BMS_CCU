package com.example.bms_ccu.data.database

import android.location.Location
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [LocationModel::class],
    version = 1
)
abstract class LocationDatabase : RoomDatabase(){
    abstract fun getLocationDao(): LocationDao

    companion object {
        @Volatile var instance : LocationDatabase? = null
        private val LOCK = Any()

        operator  fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LocationDatabase::class.java,
            "locationdb"
        ).fallbackToDestructiveMigration().build()
    }
}