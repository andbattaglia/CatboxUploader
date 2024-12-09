package com.battman.catboxuploader.photos.di

import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.catboxuploader.photos.impl.repositories.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataBindModule {

    @Binds
    @Singleton
    fun bindMediaRepository(repository: PhotoRepository): IPhotoRepository
}
