package com.example.core.data.preferences

import android.content.SharedPreferences
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.preferences.Preferences.Companion.KEY_ACTIVITY_LEVEL
import com.example.core.domain.preferences.Preferences.Companion.KEY_AGE
import com.example.core.domain.preferences.Preferences.Companion.KEY_CARB_RATIO
import com.example.core.domain.preferences.Preferences.Companion.KEY_FAT_RATIO
import com.example.core.domain.preferences.Preferences.Companion.KEY_GENDER
import com.example.core.domain.preferences.Preferences.Companion.KEY_GOAL_TYPE
import com.example.core.domain.preferences.Preferences.Companion.KEY_HEIGHT
import com.example.core.domain.preferences.Preferences.Companion.KEY_PROTEIN_RATIO
import com.example.core.domain.preferences.Preferences.Companion.KEY_SHOULD_SHOW_ONBOARDING
import com.example.core.domain.preferences.Preferences.Companion.KEY_WEIGHT


class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {

    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(KEY_HEIGHT, height)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPref.edit()
            .putString(KEY_GOAL_TYPE, goalType.name)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPref.edit()
            .putString(KEY_ACTIVITY_LEVEL, activityLevel.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun saveShouldShowOnbording(shouldShow: Boolean) {
        sharedPref
            .edit()
            .putBoolean(KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    // by default we wanna show onboarding
    override fun loadShouldShowOnbording(): Boolean {
        return sharedPref.getBoolean(KEY_SHOULD_SHOW_ONBOARDING, true)
    }

    override fun loadUserInfo(): UserInfo {
        val gender = sharedPref.getString(KEY_GENDER, null)
        val age = sharedPref.getInt(KEY_AGE, -1)
        val weight = sharedPref.getFloat(KEY_WEIGHT, -1f)
        val height = sharedPref.getInt(KEY_HEIGHT, -1)
        val goalType = sharedPref.getString(KEY_GOAL_TYPE, null)
        val activityLevel = sharedPref.getString(KEY_ACTIVITY_LEVEL, null)
        val carbRatio = sharedPref.getFloat(KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(gender ?: "male"),
            age = age,
            weight = weight,
            height = height,
            goalType = GoalType.fromString(goalType ?: "keep_weight"),
            activityLevel = ActivityLevel.fromString(activityLevel ?: "medium"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }
}