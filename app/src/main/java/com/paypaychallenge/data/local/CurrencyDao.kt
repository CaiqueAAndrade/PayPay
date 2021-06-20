package com.paypaychallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paypaychallenge.model.LiveCurrencyToJsonString
import com.paypaychallenge.util.Constants

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${Constants.Room.LIVE_CURRENCY_TABLE_NAME}")
    suspend fun getLiveCurrency(): LiveCurrencyToJsonString

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(liveCurrencyToJsonString: LiveCurrencyToJsonString)

    @Query("DELETE FROM ${Constants.Room.LIVE_CURRENCY_TABLE_NAME}")
    suspend fun deleteAll()
}