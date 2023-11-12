package com.example.data.mapper

import com.example.data.local.entity.TrackedFoodEntity
import com.example.domain.model.MealType
import com.example.domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        id = id,
        foodName = this.foodName,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.fat,
        imageUrl = this.imageUrl,
        mealType = MealType.fromString(this.type),
        amount = this.amount,
        date = LocalDate.of(this.year, this.month, this.dayOfMonth),
        calories = this.calories
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        id = id,
        foodName = this.foodName,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.fat,
        imageUrl = this.imageUrl,
        type = this.mealType.mealType,
        amount = this.amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = this.calories
    )
}
