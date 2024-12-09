package com.battman.catboxuploader.photos.di

import com.battman.catboxuploader.photos.impl.datasource.CatboxApiContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesCatboxApiContract(
        client: OkHttpClient,
    ): CatboxApiContract {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(client)
            .build()
            .create(CatboxApiContract::class.java)
    }
}
