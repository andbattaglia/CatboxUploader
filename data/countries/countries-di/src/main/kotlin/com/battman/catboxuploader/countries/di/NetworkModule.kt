package com.battman.catboxuploader.countries.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.battman.catboxuploader.countries.impl.datasource.CountriesApiContract
import com.battman.catboxuploader.countries.impl.datasource.CountriesHeaderInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesCountriesApiContract(
        json: Json,
        client: OkHttpClient,
        interceptor: CountriesHeaderInterceptor,
    ): CountriesApiContract {
        val httpClient = client.newBuilder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(httpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(CountriesApiContract::class.java)
    }
}
