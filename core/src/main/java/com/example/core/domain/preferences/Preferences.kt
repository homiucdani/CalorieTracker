package com.example.core.domain.preferences

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo

// why is an interface? we wanna have abstraction on our code, we define some methods and every "feature" like
// onboarding or tracker can implement its own code into the methods, so we dont have repetitive code
// its like having a repository interface, we have an implementation repository, but we inject into dagger only the abstraction
// so dagger dosent know what kind of preference is, its sharedpref or datastore, so it dosent know the internal code behind this methods
interface Preferences {

    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveGoalType(goalType: GoalType)
    fun saveActivityLevel(activityLevel: ActivityLevel)

    // this functions saves how many carbs, proteins, fats you wanna eat per day, and this 3 values need to lead to 100%
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): UserInfo

    fun saveShouldShowOnbording(shouldShow: Boolean)
    fun loadShouldShowOnbording(): Boolean

    companion object {
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_HEIGHT = "height"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_CARB_RATIO = "card_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
        const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
    }
}