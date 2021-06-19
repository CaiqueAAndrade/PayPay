package com.paypaychallenge

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.paypaychallenge.data.remote.InternetConnectionListener
import com.paypaychallenge.di.networkModule
import com.paypaychallenge.di.repositoryModule
import com.paypaychallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PayPayChallengeApplication: Application(), InternetConnectionListener{

    private lateinit var internetConnectionListener: InternetConnectionListener

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PayPayChallengeApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    networkModule
                )
            )
        }

        setInternetConnectionListener(this)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun setInternetConnectionListener(listener: InternetConnectionListener) {
        this.internetConnectionListener = listener
    }

    override fun onInternetUnavailable() {
        if (this::internetConnectionListener.isInitialized) {
            internetConnectionListener.onInternetUnavailable()
        }
    }
}