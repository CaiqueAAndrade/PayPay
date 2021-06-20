package com.paypaychallenge.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.paypaychallenge.data.local.CurrencyDao
import com.paypaychallenge.data.remote.CurrencyLayerApi
import com.paypaychallenge.data.remote.Result
import com.paypaychallenge.extensions.plusThirtyMinutes
import com.paypaychallenge.model.LiveCurrencyDao
import com.paypaychallenge.model.LiveCurrencyResponse
import com.paypaychallenge.util.Constants
import com.paypaychallenge.util.Utils
import org.koin.android.ext.android.inject
import java.util.*


class CurrencyConverterRepository(
    private val service: CurrencyLayerApi,
    mApplication: Application,
    private val currencyDao: CurrencyDao
) {

    private val prefs: SharedPreferences by mApplication.inject()
    private var editor: SharedPreferences.Editor = prefs.edit()
    private val calendar = Calendar.getInstance()
    private lateinit var liveCurrencyDao: LiveCurrencyDao

    suspend fun getLiveCurrency(): Result<LiveCurrencyResponse> {
        return if (hasPassedThirtyMinutes()) {
            setupApiCallTimer()
            service.getLiveDollarsCurrency(
                Constants.ASSESS_KEY
            )
        } else {
            liveCurrencyDao = currencyDao.getLiveCurrency()
            if (this::liveCurrencyDao.isInitialized.not()) {
                setupApiCallTimer()
                service.getLiveDollarsCurrency(
                    Constants.ASSESS_KEY
                )
            } else {
                val liveCurrencyResponse = Utils.stringToLiveCurrency(liveCurrencyDao.liveCurrency)
                Result.Success(liveCurrencyResponse)
            }
        }
    }

    private fun setupApiCallTimer() {
        editor.putLong(Constants.API_CALL_TIMER_KEY, calendar.timeInMillis.plusThirtyMinutes())
            .apply()
    }

    private fun hasPassedThirtyMinutes(): Boolean {
        val dateToMillis = prefs.getLong(Constants.API_CALL_TIMER_KEY, 0)
        return Utils.compareTimeToMillis(calendar.timeInMillis, dateToMillis)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(liveCurrencyResponse: LiveCurrencyResponse) {
        val liveCurrencyToString = Utils.liveCurrencyToString(liveCurrencyResponse)
        val liveCurrencyDao = LiveCurrencyDao(0, liveCurrencyToString)
        currencyDao.deleteAll()
        currencyDao.insert(liveCurrencyDao)
    }
}