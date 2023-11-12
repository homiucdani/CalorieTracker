package com.example.presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.model.GoalType
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.ActionButton
import com.example.presentation.components.SelectableButton
import core.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GoalScreen(
    onNextClick: () -> Unit,
    goalViewModel: GoalViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    val goalType = goalViewModel.goalType

    LaunchedEffect(key1 = true) {
        goalViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> onNextClick()
                is UiEvent.NavigateUp -> Unit
                is UiEvent.ShowSnackbar -> Unit
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
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                style = MaterialTheme.typography.headlineSmall
            )

            Modifier.height(spacing.spaceMedium)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = goalType is GoalType.LoseWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        goalViewModel.onActivityLevelChange(GoalType.LoseWeight)
                    }
                )

                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = goalType is GoalType.KeepWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        goalViewModel.onActivityLevelChange(GoalType.KeepWeight)
                    }
                )

                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = goalType is GoalType.GainWeight,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        goalViewModel.onActivityLevelChange(GoalType.GainWeight)
                    }
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                goalViewModel.onNextClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceLarge)
        )
    }
}