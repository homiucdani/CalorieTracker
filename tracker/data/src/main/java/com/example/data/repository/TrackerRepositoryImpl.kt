package com.example.data.repository

import com.example.data.local.TrackerDao
import com.example.data.mapper.toTrackableFood
import com.example.data.mapper.toTrackedFood
import com.example.data.mapper.toTrackedFoodEntity
import com.example.data.remote.OpenFoodApi
import com.example.domain.model.TrackableFood
import com.example.domain.model.TrackedFood
import com.example.domain.repository.TrackerRepository
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