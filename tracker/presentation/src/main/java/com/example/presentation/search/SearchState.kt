package com.example.presentation.search

data class SearchState(
    val query:String = "",
    val isHintVisible: Boolean = false,
    val isSearching:Boolean = false,
    val trackableFoodUiState: List<TrackableFoodUiState> = emptyList()
)
