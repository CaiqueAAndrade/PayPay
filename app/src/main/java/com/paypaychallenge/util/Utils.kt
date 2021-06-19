package com.paypaychallenge.util

import com.paypaychallenge.model.Quote

object Utils {
    private const val BASE_DOLLAR_VALUE = 1.0

    fun exchangeCurrency(
        currentCurrency: Double,
        conversionCurrency: Double,
        selectedValue: Double
    ): Double {
        return (BASE_DOLLAR_VALUE / currentCurrency * conversionCurrency) * selectedValue
    }

    fun getExchangedQuotesList(
        currencyCode: String,
        quotesToMap: Map<String, Double>,
        quotesToArray: List<Quote>,
        selectedValue: Double
    ): ArrayList<Quote> {

        val currency: Double = quotesToMap.getValue("USD$currencyCode")
        val convertedQuotes: ArrayList<Quote> = arrayListOf()
        for (n in quotesToArray.indices) {
            convertedQuotes.add(
                Quote(
                    quotesToArray[n].currencyCode,
                    exchangeCurrency(currency, quotesToArray[n].currencyValue, selectedValue)
                )
            )
        }
        return convertedQuotes
    }
}