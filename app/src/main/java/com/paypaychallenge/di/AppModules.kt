package com.paypaychallenge.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.paypaychallenge.data.local.CurrencyDatabase
import com.paypaychallenge.data.remote.provideOkHttpClient
import com.paypaychallenge.data.remote.provideServiceCurrencyLayer
import com.paypaychallenge.repository.CurrencyConverterRepository
import com.paypaychallenge.ui.viewmodel.CurrencyConverterViewModel
import com.paypaychallenge.util.Constants
import com.paypaychallenge.util.Constants.PAYPAY_PREFERENCES_KEY
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { CurrencyConverterRepository(get(), androidApplication(), get()) }
}

val viewModelModule = module {
    viewModel { CurrencyConverterViewModel(androidApplication(), get()) }
}

val networkModule = module {
    single { provideOkHttpClient(androidContext()) }
    single { provideServiceCurrencyLayer(get()) }
}

val preferencesModule = module {
    single { getSharedPreferences(androidApplication()) }
}

val daoModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CurrencyDatabase::class.java,
            Constants.Room.CURRENCY_DATABASE_NAME
        )
            .build()
    }
    single { get<CurrencyDatabase>().currencyDao() }
}

private fun getSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(
    PAYPAY_PREFERENCES_KEY, Context.MODE_PRIVATE
)