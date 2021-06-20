package com.paypaychallenge.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.paypaychallenge.PayPayChallengeApplication
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

fun provideOkHttpClient(context: Context): OkHttpClient.Builder {
    val timeout = 30L

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

    val client = OkHttpClient.Builder()
    client.apply {
        connectTimeout(timeout, TimeUnit.SECONDS)
        readTimeout(timeout, TimeUnit.SECONDS)
        writeTimeout(timeout, TimeUnit.SECONDS)

        client.cookieJar(JavaNetCookieJar(cookieManager))

        addInterceptor(interceptor)
        addInterceptor(object : NetworkConnectionInterceptor() {
            override fun isInternetAvailable(): Boolean {
                return (context as PayPayChallengeApplication).isInternetAvailable()
            }

            override fun onInternetUnavailable() {
                (context as PayPayChallengeApplication).onInternetUnavailable()
            }

        })
    }

    return client
}

fun provideServiceCurrencyLayer(client: OkHttpClient.Builder): CurrencyLayerApi {
    // In case that currencylayer.com exceed number of calls use my apiary mock to test
    // Just change the .baseUrl to the line bellow
    //        .baseUrl("https://private-7021a4-currencylayer.apiary-mock.com/")
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.currencylayer.com/")
        .addCallAdapterFactory(CallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client.build())
        .build()

    return retrofit.create(CurrencyLayerApi::class.java)
}