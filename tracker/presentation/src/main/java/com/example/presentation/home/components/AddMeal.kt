package com.example.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.LocalSpacing
import core.R

@Composable
fun AddMeal(
    mealType: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(60.dp))
            .padding(horizontal = spacing.spaceSmall, vertical = spacing.spaceSmall)
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(60.dp)
            )
            .padding(spacing.spaceSmall)
            .clickable {
                onClick()
            }.then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_meal),
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(
            text =  mealType,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}