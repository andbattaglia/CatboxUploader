package com.battman.data.network.extensions

import arrow.retrofit.adapter.either.networkhandling.CallError
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.ErrorType.Api.Network
import com.battman.catboxuploader.domain.models.ErrorType.Api.NotFound
import com.battman.catboxuploader.domain.models.ErrorType.Api.Server
import com.battman.catboxuploader.domain.models.ErrorType.Api.ServiceUnavailable
import com.battman.catboxuploader.domain.models.ErrorType.Unknown

fun CallError.toErrorType(): ErrorType =
    when (this) {
        is HttpError -> {
            when (this.code) {
                404 -> NotFound
                500 -> Server
                503 -> ServiceUnavailable
                else -> Unknown()
            }
        }
        is IOError -> {
            Network
        }

        is UnexpectedCallError -> {
            Unknown()
        }
    }