package com.paypaychallenge.extensions

fun Long.plusThirtyMinutes(): Long {
    return this + (30 * 60 * 1000)
}