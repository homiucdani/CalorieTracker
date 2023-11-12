package com.example.presentation.search

import com.example.domain.model.TrackableFood

// this class is only for the ui, cuz when we expand, enter a value into the ui
//then we want that value to be specific for one item
// we could pass trackableFood only in the "searchState" but we'll need to extend that model class with ui related stuff which is not good
data class TrackableFoodUiState(
    val trackableFood: TrackableFood,
    val amount: String = "",
    val isExpanded:Boolean = false
)
