package com.paypaychallenge.extensions

import java.text.NumberFormat
import java.util.*

fun Double.roundUpToCurrency(currencyCode: String): String {
    return try {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 6
        format.currency = Currency.getInstance(currencyCode)
        format.format(this)
    } catch (e: Exception) {
        ("%.6f".format(this))
    }

}