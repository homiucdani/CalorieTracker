package com.example.presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.ActionButton
import com.example.presentation.components.UnitTextField
import core.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NutrientGoalScreen(
    onNextClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    nutrientGoalViewModel: NutrientGoalViewModel = hiltViewModel()
) {

    val localContext = LocalContext.current
    val spacing = LocalSpacing.current
    val nutrientGoalState = nutrientGoalViewModel.nutrientGoalState

    LaunchedEffect(key1 = true) {
        nutrientGoalViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNextClick()
                }

                is UiEvent.NavigateUp -> Unit

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message.asString(localContext))
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = nutrientGoalState.carbsRatio,
                onValueChange = {
                    nutrientGoalViewModel.onEvent(
                        NutrientGoalEvent.OnCarbRatioEnter(
                            it
                        )
                    )
                },
                unit = stringResource(id = R.string.percent_carbs)
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = nutrientGoalState.proteinRatio,
                onValueChange = {
                    nutrientGoalViewModel.onEvent(
                        NutrientGoalEvent.OnProteinRatioEnter(
                            it
                        )
                    )
                },
                unit = stringResource(id = R.string.percent_proteins)
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = nutrientGoalState.fatRatio,
                onValueChange = {
                    nutrientGoalViewModel.onEvent(
                        NutrientGoalEvent.OnFatRatioEnter(
                            it
                        )
                    )
                },
                unit = stringResource(id = R.string.percent_fats)
            )
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                nutrientGoalViewModel.onEvent(NutrientGoalEvent.OnNextClick)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceLarge)
        )
    }
}