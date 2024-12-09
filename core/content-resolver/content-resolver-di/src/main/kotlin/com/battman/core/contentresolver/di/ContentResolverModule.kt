package com.battman.core.contentresolver.di

import android.content.ContentResolver
import android.content.Context
import com.battman.core.contentresolver.api.IContentResolverHelper
import com.battman.core.contentresolver.impl.ContentResolverHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContentResolverModule {

    @Provides
    @Singleton
    fun providesContentResolver(
        @ApplicationContext context: Context,
    ): ContentResolver = context.contentResolver

    @Provides
    fun providesContentResolverHelper(
        contentResolver: ContentResolver,
    ): IContentResolverHelper = ContentResolverHelper(contentResolver)
}
