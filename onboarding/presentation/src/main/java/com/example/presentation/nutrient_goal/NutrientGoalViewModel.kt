package com.example.presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.use_case.FilterOutDigits
import com.example.core.util.UiEvent
import com.example.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {

    var nutrientGoalState by mutableStateOf(NutrientGoalState())

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                nutrientGoalState =
                    nutrientGoalState.copy(carbsRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnProteinRatioEnter -> {
                nutrientGoalState =
                    nutrientGoalState.copy(proteinRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnFatRatioEnter -> {
                nutrientGoalState = nutrientGoalState.copy(fatRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrients(
                    nutrientGoalState.carbsRatio,
                    nutrientGoalState.proteinRatio,
                    nutrientGoalState.fatRatio
                )

                when (result) {
                    is ValidateNutrients.NutrientResult.Success -> {
                        preferences.saveCarbRatio(result.carbRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.emit(UiEvent.Navigate)
                        }
                    }

                    is ValidateNutrients.NutrientResult.Error -> {
                        viewModelScope.launch {
                            _uiEvent.emit(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                }
            }
        }
    }
}