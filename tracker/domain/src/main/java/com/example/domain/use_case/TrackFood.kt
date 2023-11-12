package com.example.domain.use_case

import com.example.domain.model.MealType
import com.example.domain.model.TrackableFood
import com.example.domain.model.TrackedFood
import com.example.domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        trackableFood: TrackableFood, // the search returns a trackable food
        amount: Int, // the amount we put from the search
        mealType: MealType,
        date: LocalDate
    ) {
        repository.upsertTrackedFood(
            TrackedFood(
                foodName = trackableFood.foodName,
                carbs = ((trackableFood.carbsPer100g / 100f) * amount).roundToInt(),
                protein = ((trackableFood.proteinsPer100g / 100f) * amount).roundToInt(),
                fat = ((trackableFood.fatPer100g / 100f) * amount).roundToInt(),
                calories = ((trackableFood.caloriesPer100g / 100f) * amount).roundToInt(),
                imageUrl = trackableFood.image,
                mealType = mealType,
                amount = amount,
                date = date
            )
        )
    }
}