package com.paypaychallenge.model

data class LiveCurrencyResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)
