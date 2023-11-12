package com.example.presentation.activity

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
import com.example.core.domain.model.ActivityLevel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.ActionButton
import com.example.presentation.components.SelectableButton
import core.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ActivityScreen(
    onNextClick: () -> Unit,
    activityLevelViewModel: ActivityViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    val activityLevel = activityLevelViewModel.activityLevel

    LaunchedEffect(key1 = true) {
        activityLevelViewModel.uiEvent.collectLatest { event ->
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
                text = stringResource(id = R.string.whats_your_activity_level),
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
                    text = stringResource(id = R.string.low),
                    isSelected = activityLevel is ActivityLevel.Low,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        activityLevelViewModel.onActivityLevelChange(ActivityLevel.Low)
                    }
                )

                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = activityLevel is ActivityLevel.Medium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        activityLevelViewModel.onActivityLevelChange(ActivityLevel.Medium)
                    }
                )

                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = activityLevel is ActivityLevel.High,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = {
                        activityLevelViewModel.onActivityLevelChange(ActivityLevel.High)
                    }
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                activityLevelViewModel.onNextClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceLarge)
        )
    }
}