package com.battman.catboxuploader.uploader.di

import com.battman.catboxuploader.uploader.api.repositories.IAlbumRepository
import com.battman.catboxuploader.uploader.impl.repositories.AlbumRepository
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
    fun bindAlbumRepository(repository: AlbumRepository): IAlbumRepository
}
