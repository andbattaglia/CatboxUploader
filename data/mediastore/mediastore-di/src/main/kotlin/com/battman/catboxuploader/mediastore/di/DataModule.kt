package com.battman.catboxuploader.mediastore.di

import com.battman.catboxuploader.mediastore.api.repositories.IMediaStoreRepository
import com.battman.catboxuploader.mediastore.impl.repositories.MediaStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindMediaRepository(repository: MediaStoreRepository): IMediaStoreRepository
}
