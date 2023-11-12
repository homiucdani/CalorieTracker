package com.example.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.LocalSpacing
import com.example.presentation.components.UnitDisplay
import com.example.presentation.home.Meal
import core.R

@Composable
fun ExpandableMeal(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onToggleClick: () -> Unit,
    meal: Meal
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium)
            .clickable {
                onToggleClick()
            }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = meal.drawableRes),
            contentDescription = meal.name.asString(context)
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = meal.name.asString(context),
                    style = MaterialTheme.typography.headlineSmall
                )
                Icon(
                    imageVector = if (meal.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (meal.isExpanded) stringResource(id = R.string.extend) else stringResource(
                        id = R.string.collapse
                    )
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnitDisplay(
                    amount = meal.calories,
                    unit = stringResource(id = R.string.kcal)
                )
                Row {
                    NutrientInfo(
                        name = stringResource(id = R.string.carbs),
                        amount = meal.carbs,
                        unit = stringResource(id = R.string.grams),
                        modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                    )

                    NutrientInfo(
                        name = stringResource(id = R.string.protein),
                        amount = meal.protein,
                        unit = stringResource(id = R.string.grams),
                        modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                    )

                    NutrientInfo(
                        name = stringResource(id = R.string.fat),
                        amount = meal.fat,
                        unit = stringResource(id = R.string.grams),
                        modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(spacing.spaceMedium))
    AnimatedVisibility(
        visible = meal.isExpanded,
        enter = fadeIn() + expandIn(expandFrom = Alignment.CenterStart),
        exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.BottomStart)
    ) {
        content()
    }
}