package com.battman.catboxuploader.countries.impl.datasource

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.battman.catboxuploader.countries.impl.models.CountryNetworkModel
import retrofit2.http.GET

interface CountriesApiContract {

    @GET("geographics/countries/")
    suspend fun countries(): Either<CallError, List<CountryNetworkModel>>
}
