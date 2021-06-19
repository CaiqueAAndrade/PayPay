package com.paypaychallenge.di

import com.paypaychallenge.data.remote.provideOkHttpClient
import com.paypaychallenge.data.remote.provideServiceCurrencyLayer
import com.paypaychallenge.repository.CurrencyConverterRepository
import com.paypaychallenge.ui.viewmodel.CurrencyConverterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { CurrencyConverterRepository(get()) }
}

val viewModelModule = module {
    viewModel { CurrencyConverterViewModel(androidApplication(), get()) }
}

val networkModule = module {
    single { provideOkHttpClient(androidContext()) }
    single { provideServiceCurrencyLayer(get()) }
}
