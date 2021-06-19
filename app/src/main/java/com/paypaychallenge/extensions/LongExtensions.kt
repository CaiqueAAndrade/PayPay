package com.paypaychallenge.extensions

fun Long.plusThirtyMinutes(): Long {
    return this + (5 * 60 * 1000)
}