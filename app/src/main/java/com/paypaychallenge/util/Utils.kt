package com.paypaychallenge.util

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.paypaychallenge.model.LiveCurrencyResponse
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

    fun compareTimeToMillis(startTime: Long, endTime: Long): Boolean {
        if (endTime <= 0) {
            return true
        }
        if (startTime < endTime) {
            return false
        }
        return true
    }

    fun stringToLiveCurrency(value: String): LiveCurrencyResponse? {
        return Gson().fromJson(value, object : TypeToken<LiveCurrencyResponse>() {}.type)
    }

    fun liveCurrencyToString(value: LiveCurrencyResponse?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}