package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {

        val allNutriments = trackedFoods
            .groupBy {// calculate nutriments for a given mealType
                it.mealType
            }.mapValues { entry -> // sum up all the nutriments values
                val mealType = entry.key
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = mealType
                )
            }

        // get all carbs not only for a specific meal, ex: get me all the carbs that i ate in this day (breakfast,launch etc)
        // so i can calculate the goal that the user have
        val totalCarbsAte = allNutriments.values.sumOf { it.carbs }
        val totalProteinAte = allNutriments.values.sumOf { it.protein }
        val totalFatAte = allNutriments.values.sumOf { it.fat }
        val totalCaloriesAte = allNutriments.values.sumOf { it.calories }

        // this gives us the goal that the user have for a specific day
        val userInfo = preferences.loadUserInfo()
        val caloryGoal = dailyCaloryRequirement(userInfo)


        //total carbsAte may be 40% which is 0.4
        // divided by 4 cuz 1g of carbs = 4 calories, 1g of fat = 9 calories
        val carbsGoal = (caloryGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbsAte = totalCarbsAte,
            totalProteinAte = totalProteinAte,
            totalFatAte = totalFatAte,
            totalCaloriesAte = totalCaloriesAte,
            mealNutriments = allNutriments
        )
    }

    // Basal Metabolic Rate (BMR) is the number of calories you burn
    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }

            is Gender.Female -> {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    // depends on the user choosed options, gives as the name says
    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }


    // like breakfast, launch etc
    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbsAte: Int,
        val totalProteinAte: Int,
        val totalFatAte: Int,
        val totalCaloriesAte: Int,
        val mealNutriments: Map<MealType, MealNutrients>
    )
}