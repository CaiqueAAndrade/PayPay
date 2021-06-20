package com.paypaychallenge.extensions


fun String.currencyToDouble(): Double {
    return try {
        this.replace(("[^\\d.]").toRegex(), "").toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun String.toNumericString() = this.filter { it.isDigit() }