package com.example.presentation.height

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun HeightScreen(
    onNextClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    heightViewModel: HeightViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val localContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        heightViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNextClick()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message.asString(localContext))
                }

                is UiEvent.NavigateUp -> Unit
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
            Text(text = stringResource(id = R.string.whats_your_height))

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = heightViewModel.height,
                onValueChange = heightViewModel::onHeightChange,
                unit = stringResource(id = R.string.cm)
            )
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                heightViewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(spacing.spaceLarge)
        )
    }
}