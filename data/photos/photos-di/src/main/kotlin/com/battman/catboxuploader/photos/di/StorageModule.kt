package com.battman.catboxuploader.photos.di

import android.content.ContentResolver
import android.content.Context
import com.battman.catboxuploader.photos.impl.datasource.ContentResolverDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun providesContentResolver(
        @ApplicationContext context: Context,
    ): ContentResolver = context.contentResolver

    @Provides
    @Singleton
    fun providesContentResolverDatasource(
        contentResolver: ContentResolver,
    ): ContentResolverDatasource = ContentResolverDatasource(contentResolver)
}
