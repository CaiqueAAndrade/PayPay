package com.paypaychallenge

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.paypaychallenge.data.remote.InternetConnectionListener
import com.paypaychallenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PayPayChallengeApplication : Application() {

    private lateinit var internetConnectionListener: InternetConnectionListener

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PayPayChallengeApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    networkModule,
                    daoModule,
                    preferencesModule
                )
            )
        }
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun onInternetUnavailable() {
        if (this::internetConnectionListener.isInitialized) {
            internetConnectionListener.onInternetUnavailable()
        }
    }

    fun setInternetConnectionListener(listener: InternetConnectionListener) {
        this.internetConnectionListener = listener
    }
}