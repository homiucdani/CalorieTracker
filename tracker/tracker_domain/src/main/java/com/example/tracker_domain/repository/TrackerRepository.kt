package com.example.tracker_domain.repository

import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        foodName: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>>

    suspend fun upsertTrackedFood(food: TrackedFood)

    suspend fun deleteTrackedFood(food: TrackedFood)

    fun getFoodForDate(localeDate: LocalDate): Flow<List<TrackedFood>>
}