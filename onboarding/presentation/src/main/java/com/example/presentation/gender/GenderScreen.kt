package com.example.presentation.gender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.model.Gender
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.ActionButton
import com.example.presentation.components.SelectableButton
import core.R

@Composable
fun GenderScreen(
    onNextClick: () -> Unit,
    genderViewModel: GenderViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val genderState = genderViewModel.selectedGender

    LaunchedEffect(key1 = true) {
        genderViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNextClick()
                is UiEvent.NavigateUp -> Unit
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableButton(
                    text = stringResource(id = R.string.male),
                    isSelected = when (genderState) {
                        Gender.Male -> true
                        else -> false
                    },
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { genderViewModel.onGenderClick(Gender.Male) },
                    textStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                SelectableButton(
                    text = stringResource(id = R.string.female),
                    isSelected = when (genderState) {
                        Gender.Female -> true
                        else -> false
                    },
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { genderViewModel.onGenderClick(Gender.Female) },
                    textStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal)
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                genderViewModel.onNextClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing.spaceLarge)
        )
    }
}