package com.paypaychallenge.data.remote

import com.paypaychallenge.model.LiveCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApi {

    @GET("/live")
    suspend fun getLiveDollarsCurrency(
        @Query("access_key") accessKey: String
    ): Result<LiveCurrencyResponse>
}