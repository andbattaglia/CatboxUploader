package com.battman.catboxuploader.feature.common.extensions

import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.feature.common.R

fun ErrorType.toMessageTitle(): Int =
    when (this) {
        is ErrorType.Api.Network -> R.string.error_network_title
        is ErrorType.Api.ServiceUnavailable -> R.string.error_service_unavailable_title
        is ErrorType.Api.NotFound -> R.string.error_not_found_title
        is ErrorType.Api.Server -> R.string.error_server_title
        is ErrorType.Unknown -> R.string.error_unknown_title
    }

fun ErrorType.toMessageDescription(): Int =
    when (this) {
        is ErrorType.Api.Network -> R.string.error_network_description
        is ErrorType.Api.ServiceUnavailable -> R.string.error_service_unavailable_description
        is ErrorType.Api.NotFound -> R.string.error_not_found_description
        is ErrorType.Api.Server -> R.string.error_server_description
        is ErrorType.Unknown -> R.string.error_unknown_description
    }
