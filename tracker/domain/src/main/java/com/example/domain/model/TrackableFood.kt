package com.example.domain.model

// if we are in the search food screen, then all the foods out there are trackable
data class TrackableFood(
    val foodName: String,
    val image: String?,
    val caloriesPer100g: Int,
    val carbsPer100g: Int,
    val proteinsPer100g: Int,
    val fatPer100g: Int
)
