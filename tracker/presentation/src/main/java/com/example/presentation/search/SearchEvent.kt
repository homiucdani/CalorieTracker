package com.example.presentation.search

import com.example.domain.model.MealType
import com.example.domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnSearchChange(val query: String) : SearchEvent()

    object OnSearch : SearchEvent()

    data class OnToggleTrackableFood(val trackableFood: TrackableFood) : SearchEvent()

    data class OnAmountForFoodChange(
        val trackableFood: TrackableFood,
        val amount: String
    ) : SearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ) : SearchEvent()

    data class OnSearchFocusChange(val isFocused:Boolean) : SearchEvent()
}

