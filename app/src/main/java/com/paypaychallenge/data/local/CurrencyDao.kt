package com.paypaychallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paypaychallenge.model.LiveCurrencyDao
import com.paypaychallenge.util.Constants

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${Constants.Room.LIVE_CURRENCY_TABLE_NAME}")
    fun getLiveCurrency(): LiveCurrencyDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(liveCurrencyDao: LiveCurrencyDao)

    @Query("DELETE FROM ${Constants.Room.LIVE_CURRENCY_TABLE_NAME}")
    suspend fun deleteAll()
}