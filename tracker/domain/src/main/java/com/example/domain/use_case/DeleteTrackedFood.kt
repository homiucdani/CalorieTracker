package com.example.domain.use_case

import com.example.domain.model.TrackedFood
import com.example.domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        trackedFood: TrackedFood
    ) {
        return repository.deleteTrackedFood(trackedFood)
    }
}