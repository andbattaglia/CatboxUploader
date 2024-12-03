package com.battman.core.network.api.provider

import com.battman.core.network.api.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientInstanceProvider @Inject constructor() {

    fun provideOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(getLoggingInterceptor())
        .build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingBody = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingBody.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingBody.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingBody
    }
}
