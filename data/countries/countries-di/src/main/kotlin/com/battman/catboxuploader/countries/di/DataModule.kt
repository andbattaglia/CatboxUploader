package com.battman.catboxuploader.countries.di

import com.battman.catboxuploader.countries.api.ICountryRepository
import com.battman.catboxuploader.countries.impl.repositories.CountryRepository
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
    fun bindCountriesRepository(repository: CountryRepository): ICountryRepository
}
