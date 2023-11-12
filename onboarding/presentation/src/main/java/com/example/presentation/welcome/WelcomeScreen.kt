package com.example.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.ActionButton
import core.R


@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit
) {
    val localSpacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(localSpacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(localSpacing.spaceMedium))

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                onNextClick()
            },
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}