package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.SendSelectedPhotosToUploadUseCase.Params
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class SendSelectedPhotosToUploadUseCase @Inject constructor()
    : IUseCase<Params, Either<ErrorType, Unit>>() {

    data class Params(
        val selectedPhotoIds: List<Photo>,
    )

    override suspend fun execute(params: Params) = Unit.right()
}