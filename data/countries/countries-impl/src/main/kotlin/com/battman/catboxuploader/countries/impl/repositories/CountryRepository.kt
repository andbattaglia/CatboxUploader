package com.battman.catboxuploader.countries.impl.repositories

import arrow.core.Either
import arrow.core.left
import com.battman.catboxuploader.countries.api.ICountryRepository
import com.battman.catboxuploader.countries.impl.datasource.CountriesApiContract
import com.battman.catboxuploader.countries.impl.models.mapToDomain
import com.battman.catboxuploader.domain.models.Country
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.ErrorType.Unknown
import com.battman.core.dispatcher.api.IoDispatcher
import com.battman.data.network.extensions.toErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val apiContract: CountriesApiContract,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ICountryRepository {

    override suspend fun getCountries(): Either<ErrorType, List<Country>> {
        return withContext(ioDispatcher) {
            try {
                apiContract.countries()
                    .map { countries ->
                        countries.map { country ->
                            country.mapToDomain()
                        }
                    }
                    .mapLeft { error ->
                        error.toErrorType()
                    }
            } catch (e: Exception) {
                Unknown(e.message).left()
            }
        }
    }
}
