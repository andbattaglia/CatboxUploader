package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.countries.api.ICountryRepository
import com.battman.catboxuploader.domain.models.Country
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.usecases.GetCountriesUseCase.Params
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: ICountryRepository,
) : IUseCase<Params, Either<ErrorType, List<Country>>>() {

    object Params

    override suspend fun execute(params: Params) =
        countriesRepository.getCountries()
}
