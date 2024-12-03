package com.battman.catboxuploader.countries.impl.models

import com.battman.catboxuploader.domain.models.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryNetworkModel(
    @SerialName("iso")
    val iso: Int? = null,
    @SerialName("isoAlpha2")
    val isoAlpha2: String? = null,
    @SerialName("isoAlpha3")
    val isoAlpha3: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("phonePrefix")
    val phonePrefix: String? = null,
    @SerialName("phoneRegex")
    val phoneRegex: String? = null,
)

fun CountryNetworkModel?.mapToDomain(): Country =
    Country(
        iso = this?.iso ?: 0,
        isoAlpha2 = this?.isoAlpha2.orEmpty(),
        isoAlpha3 = this?.isoAlpha3.orEmpty(),
        name = this?.name.orEmpty(),
        phonePrefix = this?.phonePrefix.orEmpty(),
        phoneRegex = this?.phoneRegex.orEmpty(),
    )
