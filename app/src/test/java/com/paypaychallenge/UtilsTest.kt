package com.paypaychallenge

import com.paypaychallenge.model.LiveCurrencyResponse
import com.paypaychallenge.model.Quote
import com.paypaychallenge.util.Utils
import junit.framework.TestCase.assertEquals

import org.junit.Test
import java.lang.Double.NaN

class UtilsTest {

    private val quotesToMap = mutableMapOf(
        Pair("USDUDS", 1.0),
        Pair("USDBRL", 5.0),
        Pair("USDJPY", 110.00),
    )

    private val quotesToList = listOf(
        Quote("UDS", 1.0),
        Quote("BRL", 5.0),
        Quote("JPY", 110.0)
    )

    private val liveCurrencyResponse = LiveCurrencyResponse(
        true,
        "",
        "",
        1624205156524,
        "USD",
        quotesToMap
    )

    private val liveCurrencyResponseToString = "{\"success\":true,\"terms\":\"\",\"privacy\":\"\",\"timestamp\":1624205156524,\"source\":\"USD\",\"quotes\":{\"USDUDS\":1.0,\"USDBRL\":5.0,\"USDJPY\":110.0}}"

    @Test
    fun `exchangeCurrency returns currency exchanged calc when passed two different values and selected value`() {

        // When currency selected is 1/1 with dollar value and conversion currency is 1/5 with dollar value
        // When selected value is 1
        assertEquals(5.0, Utils.exchangeCurrency(1.0, 5.0, 1.0))

        // When currency selected is 4/1 with dollar value and conversion currency is 1/1 with dollar value
        // When selected value is 10
        assertEquals(2.5, Utils.exchangeCurrency(4.0, 1.0, 10.0))

        // When every value is 0
        assertEquals(NaN, Utils.exchangeCurrency(0.0, 0.0, 0.0))
    }

    @Test
    fun `getExchangedQuotesList returns array list of quotes exchanged when passed currency code, map of quotes, array of quotes and selected value`() {
        var convertedQuotes = Utils.getExchangedQuotesList("BRL", quotesToMap, quotesToList, 1.0)

        // When exchange BLR to USD with selected value of 1
        assertEquals(0.2, convertedQuotes[0].currencyValue)

        // When exchange BLR to BLR with selected value of 1
        assertEquals(1.0, convertedQuotes[1].currencyValue)

        // When exchange BLR to JPY with selected value of 1
        assertEquals(22.0, convertedQuotes[2].currencyValue)

        convertedQuotes = Utils.getExchangedQuotesList("BRL", quotesToMap, quotesToList, 10.0)

        // When exchange BLR to USD with selected value of 10
        assertEquals(2.0, convertedQuotes[0].currencyValue)

        // When exchange BLR to BLR with selected value of 10
        assertEquals(10.0, convertedQuotes[1].currencyValue)

        // When exchange BLR to JPY with selected value of 10
        assertEquals(220.0, convertedQuotes[2].currencyValue)

        convertedQuotes = Utils.getExchangedQuotesList("BRL", quotesToMap, quotesToList, 0.0)

        // When exchange BLR to USD with selected value of 0
        assertEquals(0.0, convertedQuotes[0].currencyValue)

        // When exchange BLR to BLR with selected value of 0
        assertEquals(0.0, convertedQuotes[1].currencyValue)

        // When exchange BLR to JPY with selected value of 0
        assertEquals(0.0, convertedQuotes[2].currencyValue)
    }

    @Test
    fun `compareTimeToMillis returns boolean indicating that passed thirty minutes when passed two millis`() {
        // When has thirty minutes difference from two times
        assertEquals(true, Utils.compareTimeToMillis(1624205156524, 1624203356524))

        // When has thirty minutes difference from 0
        assertEquals(true, Utils.compareTimeToMillis(1800000, 0))

        // When doesn't have thirty minutes difference from two times
        assertEquals(false, Utils.compareTimeToMillis(1624203356524, 1624205156524))

        // When doesn't have thirty minutes difference from 0
        assertEquals(false, Utils.compareTimeToMillis(0, 1800000))
    }

    @Test
    fun `stringToLiveCurrency return liveCurrencyResponse when passed json string`() {
        // When liveCurrencyResponse is not null return json string
        assertEquals(liveCurrencyResponseToString, Utils.liveCurrencyToString(liveCurrencyResponse))

        // When liveCurrencyResponse is null return empty string
        assertEquals("", Utils.liveCurrencyToString(null))
    }


    @Test
    fun `liveCurrencyToString return string json when passed json liveCurrencyResponse`() {
        // When string has json returns liveCurrencyResponse
        assertEquals(liveCurrencyResponse, Utils.stringToLiveCurrency(liveCurrencyResponseToString))

        // When string is empty returns null
        assertEquals(null, Utils.stringToLiveCurrency(""))
    }
}