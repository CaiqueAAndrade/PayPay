package com.paypaychallenge.extensions

fun String.currencyToDouble(): Double {
    return try {
        val formattedValue = this.toNumericString()
        (formattedValue.substring(0, formattedValue.length - 2)
                + "."
                + formattedValue.substring(formattedValue.length - 2)).toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun String.toNumericString() = this.filter { it.isDigit() }