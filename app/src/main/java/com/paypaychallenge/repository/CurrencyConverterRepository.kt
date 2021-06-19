package com.paypaychallenge.repository

import com.paypaychallenge.data.remote.Result
import com.paypaychallenge.data.remote.CurrencyLayerApi
import com.paypaychallenge.model.LiveCurrencyResponse
import com.paypaychallenge.util.Constants

class CurrencyConverterRepository(private val service: CurrencyLayerApi) {

    suspend fun getLiveCurrency(): Result<LiveCurrencyResponse> {
        return service.getLiveDollarsCurrency(
            Constants.ASSESS_KEY
        )
    }
}