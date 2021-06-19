package com.paypaychallenge.extensions

import java.lang.Exception

fun String.currencyToDouble(): Double {
    return try {
        this.replace("""[$,]""".toRegex(), "").toDouble()
    } catch (e: Exception) {
        0.0
    }
}