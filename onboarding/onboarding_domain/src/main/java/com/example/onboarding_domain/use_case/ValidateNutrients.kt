package com.example.onboarding_domain.use_case

import com.example.core.util.UiText
import core.R

class ValidateNutrients {

    operator fun invoke(
        carbRatioTest: String,
        proteinRatioTest: String,
        fatRatioTest: String,
    ): NutrientResult {
        val carbRatio = carbRatioTest.toIntOrNull()
        val proteinRatio = proteinRatioTest.toIntOrNull()
        val fatRatio = fatRatioTest.toIntOrNull()

        if (carbRatio == null || proteinRatio == null || fatRatio == null) {
            return NutrientResult.Error(message = UiText.StringResources(R.string.error_invalid_values))
        }

        // values must be 100% in order to proceed
        if (carbRatio + proteinRatio + fatRatio != 100) {
            return NutrientResult.Error(message = UiText.StringResources(R.string.error_not_100_percent))
        }

        // we divide by 100 so we get the max result = 1, ex: carbRatio = 40 then we divide by 100 = 0,4 ratio
        return NutrientResult.Success(
            carbRatio / 100f,
            proteinRatio / 100f,
            fatRatio / 100f
        )
    }

    sealed class NutrientResult {
        data class Success(
            val carbRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ) : NutrientResult()

        data class Error(val message: UiText) : NutrientResult()
    }

}