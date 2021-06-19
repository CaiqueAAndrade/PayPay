package com.paypaychallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paypaychallenge.model.LiveCurrencyDao

@Database(entities = [LiveCurrencyDao::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}