package com.example.data.mapper

import com.example.data.remote.dto.Product
import com.example.domain.model.TrackableFood
import kotlin.math.roundToInt


fun Product.toTrackableFood(): TrackableFood? {

    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.proteins100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        foodName = productName ?: return null,
        carbsPer100g = carbsPer100g,
        proteinsPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        caloriesPer100g = caloriesPer100g,
        image = image
    )
}