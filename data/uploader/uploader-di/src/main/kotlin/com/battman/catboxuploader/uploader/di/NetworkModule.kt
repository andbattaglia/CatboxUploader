package com.battman.catboxuploader.uploader.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.battman.catboxuploader.uploader.impl.BuildConfig
import com.battman.catboxuploader.uploader.impl.datasource.CatboxApiContract
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
    fun providesCatboxApiContract(
        json: Json,
        client: OkHttpClient,
    ): CatboxApiContract {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(CatboxApiContract::class.java)
    }
}
