package com.example.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.FilterOutDigits
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var searchState by mutableStateOf(SearchState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnAmountForFoodChange -> {
                searchState = searchState.copy(
                    // change the value from a food inside our list
                    trackableFoodUiState = searchState.trackableFoodUiState.map { foodFromList ->
                        if (foodFromList.trackableFood == event.trackableFood) {
                            foodFromList.copy(amount = filterOutDigits(event.amount))
                        } else {
                            foodFromList
                        }
                    }
                )
            }

            is SearchEvent.OnSearch -> {
                execSearch()
            }

            is SearchEvent.OnSearchChange -> {
                searchState = searchState.copy(query = event.query)
            }

            is SearchEvent.OnSearchFocusChange -> {
                searchState =
                    searchState.copy(isHintVisible = !event.isFocused && searchState.query.isBlank())
            }

            is SearchEvent.OnToggleTrackableFood -> {
                searchState = searchState.copy(
                    trackableFoodUiState = searchState.trackableFoodUiState.map { foodFromList ->
                        if (foodFromList.trackableFood == event.trackableFood) {
                            foodFromList.copy(isExpanded = !foodFromList.isExpanded)
                        } else {
                            foodFromList
                        }
                    }
                )
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun execSearch() {
        viewModelScope.launch {
            searchState = searchState.copy(
                isSearching = true,
                trackableFoodUiState = emptyList()
            )
            trackerUseCases.searchFood(
                searchState.query
            ).onSuccess { foodList ->
                searchState = searchState.copy(
                    isSearching = false,
                    trackableFoodUiState = foodList.map { food ->
                        TrackableFoodUiState(food)
                    },
                    query = ""
                )
            }.onFailure {
                searchState = searchState.copy(isSearching = false)
                _uiEvent.emit(UiEvent.ShowSnackbar(UiText.StringResources(core.R.string.error_something_went_wrong)))
            }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = searchState.trackableFoodUiState.find { it.trackableFood == event.food }
            trackerUseCases.trackFood(
                trackableFood = uiState?.trackableFood ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            // get back to previous screen
            _uiEvent.emit(UiEvent.NavigateUp)
        }
    }
}