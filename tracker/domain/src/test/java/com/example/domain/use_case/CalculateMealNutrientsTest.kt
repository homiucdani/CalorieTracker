package com.example.domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.domain.model.MealType
import com.example.domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    // before the tests run, this fun instantiate the objects and provides only one instance across the whole tests
    // why? cuz if ex: test A have an instance of an object and test B has another, then if test A made some changes then test B fails
    @Before
    fun setUp() {
        // mock -> simulate the behavior of objects or components ex:  if a class depends on a network service,
        // rather than making actual network calls during testing, a mock object can be created to simulate the network
        // responses and ensure predictable testing scenarios.

        val preferences = mockk<Preferences>(relaxed = true)

        // in our calculateMealNutrients we use this preferences to access only the loadUserInfo method
        every {
            preferences.loadUserInfo()
        } returns (UserInfo(
            gender = Gender.Male,
            age = 19,
            weight = 70f,
            height = 183,
            goalType = GoalType.GainWeight,
            activityLevel = ActivityLevel.Medium,
            carbRatio = 200f,
            proteinRatio = 100f,
            fatRatio = 90f
        ))
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    // basically what this tests does is, that we access the class, methods etc but we put in them our fake implementation
    // of the code
    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                foodName = "name",
                carbs = Random.nextInt(until = 100),
                protein = Random.nextInt(until = 100),
                fat = Random.nextInt(until = 100),
                mealType = MealType.fromString(
                    listOf(
                        "breakfast", "lunch",
                        "dinner", "snack"
                    ).random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }


        val result = calculateMealNutrients(trackedFoods)

        // we wanna test if the result from the calculateMealNutrients method is equal to the result of the trackedFoods
        // so those methods have the same amount of calories, it will succeed, else not
        val breakfastCalories = result.mealNutriments.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }


    @Test
    fun `Proteins for breakfast properly calculated`() {

        val trackedFood = (1..30).map {
            TrackedFood(
                foodName = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack")
                        .random()
                ),
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrients(trackedFood)

        val calculatedProteins = result.mealNutriments.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.protein }

        val expectedProteins = trackedFood
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.protein }

        assertThat(calculatedProteins).isEqualTo(expectedProteins)
    }












































}