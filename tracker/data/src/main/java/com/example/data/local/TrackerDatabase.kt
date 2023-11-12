package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.TrackedFoodEntity

@Database(
    entities = [
        TrackedFoodEntity::class
    ],
    version = 1
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val trackerDao: TrackerDao
}