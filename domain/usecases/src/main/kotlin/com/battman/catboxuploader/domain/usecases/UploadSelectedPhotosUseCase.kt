package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.usecases.UploadSelectedPhotosUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class UploadSelectedPhotosUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
)
    : IUseCase<Params, Either<ErrorType, String>>() {

    data object Params

    override suspend fun execute(params: Params) = "".right()

}