package com.example.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.example.core_ui.CarbColor
import com.example.core_ui.FatColor
import com.example.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {

    val background = MaterialTheme.colorScheme.background
    val caloriesExceedColor = MaterialTheme.colorScheme.error

    // the ratio could be 0.4 or 0.3 out of 1 which is max
    val carbWithRatio = remember {
        Animatable(0f)
    }

    val proteinWithRatio = remember {
        Animatable(0f)
    }

    val fatWithRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = carbs) {
        carbWithRatio.animateTo(
            ((carbs * 4f) / calorieGoal)
        )
    }

    LaunchedEffect(key1 = protein) {
        proteinWithRatio.animateTo(
            ((protein * 4f) / calorieGoal)
        )
    }

    LaunchedEffect(key1 = fat) {
        fatWithRatio.animateTo(
            ((fat * 9f) / calorieGoal)
        )
    }

    Canvas(modifier = modifier) {
        // if we have 0.4 radio we multiply with the width of the canvas, so it can draw and stop at the max point
        // if the ratio is 1 for ex then the canvas is full filled, otherwise can be at the middle or beginning
        // it will be like this: carbRatio * size.width = 0.4 * 1 = 0.4 so 0.4 is filled
        val carbsWitdh = carbWithRatio.value * size.width
        val proteinWitdh = carbWithRatio.value * size.width
        val fatWitdh = carbWithRatio.value * size.width

        if (calories <= calorieGoal) {
            // the bg is the white color, then we draw the other colors on top
            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = FatColor,
                size = Size(
                    // if we dont add all the widths from the other values, then we cannot see the red bar
                    // cuz the red bar is behind all bars, so we add the other bar width to see at the end the red bar
                    width = carbsWitdh + proteinWitdh + fatWitdh,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = ProteinColor,
                size = Size(
                    width = carbsWitdh + proteinWitdh,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = CarbColor,
                size = Size(
                    width = carbsWitdh,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            // if we are above the calories then draw on canvas a red bar
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }

}













































