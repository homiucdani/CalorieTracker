package com.example.domain.model

import java.time.LocalDate

// here we should do the same class that we have in our database, but in database we cant save a sealed class,
// but we can retrieve from there a value and return in this class ex: MealType,
// we wrapped the mealtype into a sealed class cuz there are a bunch of values that we can save as a "Unity"
// and have here clean code, so we dont have a lot of values as parameters here
// did the save for date
data class TrackedFood(
    val id: Int? = null,
    val foodName: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int
)
