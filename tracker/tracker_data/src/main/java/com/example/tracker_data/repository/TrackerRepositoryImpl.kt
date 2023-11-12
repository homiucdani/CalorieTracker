package com.example.tracker_data.repository

import com.example.tracker_data.local.TrackerDao
import com.example.tracker_data.mapper.toTrackableFood
import com.example.tracker_data.mapper.toTrackedFood
import com.example.tracker_data.mapper.toTrackedFoodEntity
import com.example.tracker_data.remote.OpenFoodApi
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        foodName: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {

        return try {
            val searchDto = api.searchFood(foodName, page, pageSize)
            Result.success(
                searchDto.products
                    .mapNotNull {
                        it.toTrackableFood()
                    }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun upsertTrackedFood(food: TrackedFood) {
        dao.upsertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodForDate(localeDate: LocalDate): Flow<List<TrackedFood>> {
        return dao
            .getFoodsForDate(localeDate.dayOfMonth, localeDate.monthValue, localeDate.year)
            .map { entities ->
                entities.map {
                    it.toTrackedFood()
                }
            }
    }

}