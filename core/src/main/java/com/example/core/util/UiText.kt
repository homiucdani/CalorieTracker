package com.example.core.util

import android.content.Context

sealed class UiText {
    // this means that we got a message from the api or from somewhere else, and the message is not from android string resoruces
    data class DynamicString(val message: String) : UiText()
    data class StringResources(val resId: Int) : UiText()

    fun asString(context:Context): String {
        // "this" refers to the class
        return when(this) {
            is DynamicString -> message
            is StringResources -> context.getString(resId)
        }
    }
}
