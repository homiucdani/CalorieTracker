package com.example.presentation.home.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.CarbColor
import com.example.core_ui.FatColor
import com.example.core_ui.LocalSpacing
import com.example.core_ui.ProteinColor
import com.example.presentation.components.UnitDisplay
import com.example.presentation.home.TrackerOverviewState
import core.R

@Composable
fun NutrientHeader(
    state: TrackerOverviewState,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    val animateCalorieCount = animateIntAsState(
        targetValue = state.totalCalories
    )
    val animateCalorieGoal = animateIntAsState(
        targetValue = state.caloriesGoal
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .padding(
                horizontal = spacing.spaceLarge,
                vertical = spacing.spaceExtraLarge
            ).then(modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                amount = animateCalorieCount.value,
                unit = stringResource(id = R.string.kcal),
                amountTextSize = 40.sp,
                amountTextColor = MaterialTheme.colorScheme.onPrimary,
                unitTextColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)
            )

            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                UnitDisplay(
                    amount = animateCalorieGoal.value,
                    unit = stringResource(id = R.string.kcal),
                    amountTextSize = 40.sp,
                    amountTextColor = MaterialTheme.colorScheme.onPrimary,
                    unitTextColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        NutrientsBar(
            carbs = state.totalCarbs,
            protein = state.totalProtein,
            fat = state.totalFat,
            calories = state.totalCalories,
            calorieGoal = state.caloriesGoal,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )

        Spacer(modifier = Modifier.height(spacing.spaceLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientBarInfo(
                value = state.totalCarbs,
                goal = state.carbsGoal,
                name = stringResource(
                    id = R.string.carbs
                ),
                color = CarbColor,
                modifier = Modifier.size(90.dp)
            )

            NutrientBarInfo(
                value = state.totalProtein,
                goal = state.proteinGoal,
                name = stringResource(
                    id = R.string.protein
                ),
                color = ProteinColor,
                modifier = Modifier.size(90.dp)
            )

            NutrientBarInfo(
                value = state.totalFat,
                goal = state.fatGoal,
                name = stringResource(
                    id = R.string.fat
                ),
                color = FatColor,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}