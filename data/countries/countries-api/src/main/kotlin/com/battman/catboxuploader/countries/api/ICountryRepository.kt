package com.battman.catboxuploader.countries.api

import arrow.core.Either
import com.battman.catboxuploader.domain.models.Country
import com.battman.catboxuploader.domain.models.ErrorType

/**
 * Interface representing a repository for managing country-related operations.
 */
interface ICountryRepository {

    /**
     * Retrieves a list of all available countries.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Country>`: A list of `Country` objects if the operation succeeds.
     */
    suspend fun getCountries(): Either<ErrorType, List<Country>>
}