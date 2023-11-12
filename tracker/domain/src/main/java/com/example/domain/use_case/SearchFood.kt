package com.example.domain.use_case

import com.example.domain.model.TrackableFood
import com.example.domain.repository.TrackerRepository

class SearchFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        foodName: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> {
        if (foodName.isBlank()) {
            return Result.success(emptyList())
        }

        return repository.searchFood(foodName.trim(), page, pageSize)
    }
}