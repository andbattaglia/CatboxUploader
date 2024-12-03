package com.battman.catboxuploader.countries.di.datasource

import arrow.retrofit.adapter.either.networkhandling.HttpError
import com.battman.catboxuploader.countries.impl.datasource.CountriesApiContract
import com.battman.core.network.test.ApiContractAbstract
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CountriesApiContractTest : ApiContractAbstract<CountriesApiContract>() {

    private lateinit var apiContract: CountriesApiContract

    @Before
    fun setup() {
        apiContract = createService(CountriesApiContract::class.java)
    }

    @Test
    fun `given countries endpoint when response is fetched then validate countries list`() = runTest {
        enqueueResponse("geographics/countries/success.json")

        val response = apiContract.countries()
        require(response.isRight())

        with(requireNotNull(response.getOrNull())) {
            assertNotNull(this)
            assertThat(this.size, `is`(4))
            assertThat(this[0].name, `is`("Aland Islands"))
        }
    }

    @Test
    fun `given countries endpoint when response fails then validate error type`() = runTest {
        enqueueResponse("geographics/countries/failure.json", 404)

        val response = apiContract.countries()
        require(response.isLeft())

        response.onLeft {
            assertTrue(it is HttpError)
            assertTrue((it as HttpError).code == 404)
        }
    }
}
