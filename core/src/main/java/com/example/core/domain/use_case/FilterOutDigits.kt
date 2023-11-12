package com.example.core.domain.use_case

class FilterOutDigits {

    operator fun invoke(digit: String): String {
        return digit.filter { it.isDigit() }
    }

}