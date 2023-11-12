package com.example.core.domain.model

// we save the name so we can put a string inside the preferences
sealed class Gender(val name: String) {
    object Male : Gender("male")
    object Female : Gender("female")

    // we cam get from preferences the name and return a Gender type so we can do smth like
    //ex: val male = pref.getString(Gender.Male.name)
    // if(Gender.fromString(male)) do smth
    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                "male" -> Male
                "female" -> Female
                else -> Female
            }
        }
    }
}
