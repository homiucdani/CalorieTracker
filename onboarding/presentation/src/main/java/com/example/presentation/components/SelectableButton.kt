package com.example.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.LocalSpacing

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(LocalSpacing.current.spaceExtraLarge),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        colors = ButtonDefaults
            .buttonColors(containerColor = if (isSelected) color else MaterialTheme.colorScheme.onPrimary),
        border = BorderStroke(
            2.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (isSelected) selectedTextColor else MaterialTheme.colorScheme.primaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall)
        )
    }
}