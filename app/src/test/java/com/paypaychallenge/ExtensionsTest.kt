package com.paypaychallenge

import com.paypaychallenge.extensions.currencyToDouble
import com.paypaychallenge.extensions.plusThirtyMinutes
import com.paypaychallenge.extensions.roundUpToCurrency
import com.paypaychallenge.extensions.toNumericString
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `roundUpToCurrency extension returns string currency when extends double`(){
        // When value is 1 USD then return formatted value to string
        assertEquals("$1.00", (1.0).roundUpToCurrency("USD"))

        // When value is 20.5798 BRL then return formatted value to string
        assertEquals("R$20.5798", (20.5798).roundUpToCurrency("BRL"))

        // When value is 500 JPY then return formatted value to string
        assertEquals("¥500.00", (500.0).roundUpToCurrency("JPY"))

        // When is empty value and currency then return 0.000000 to string
        assertEquals("0.000000", (0.00).roundUpToCurrency(""))
    }

    @Test
    fun `plusThirtyMinutes extension returns long millis plus thirty minutes when extends long`() {

        // When millis is not empty then return millis plus thirty seconds
        assertEquals(1624205156524, (1624203356524).plusThirtyMinutes())

        // When empty millis then return 0 plus thirty seconds
        assertEquals(1800000, (0).toLong().plusThirtyMinutes())
    }

    @Test
    fun `currencyToDouble extension returns double when extended string`() {
        // When string does not contain numbers then return 0 to double
        assertEquals(0.0, "Test".currencyToDouble())

        // When is empty string then return 0 to double
        assertEquals(0.0, "".currencyToDouble())

        // When value is currency with four decimal places return double with four decimal places
        assertEquals(20.5798, "R$20.5798".currencyToDouble())

        // When value is currency with two decimal places return double with two decimal places
        assertEquals(500.0, "¥500.00".currencyToDouble())
    }

    @Test
    fun `toNumericString extension returns numeric string when extends string`() {
        // When empty string return empty string
        assertEquals("", "".toNumericString())

        // When string without numbers return empty string
        assertEquals("", "Test".toNumericString())

        // When string with only one number return number to string
        assertEquals("0", "0".toNumericString())

        // When string with three not numeric values return only numeric values
        assertEquals("205798", "R$20.5798".toNumericString())

        // When string with two not numeric values return only numeric values
        assertEquals("50000", "¥500.00".toNumericString())
    }
}