package com.paypaychallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paypaychallenge.util.Constants

@Entity(tableName = Constants.Room.LIVE_CURRENCY_TABLE_NAME)
data class LiveCurrencyToJsonString(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val liveCurrency: String
)