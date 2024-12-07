package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.GetDevicePhotosUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class GetDevicePhotosUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
) : IUseCase<Params, Either<ErrorType, List<Photo>>>() {

    object Params

    override suspend fun execute(params: Params) =
        photoRepository.getDevicePhotos()
}
