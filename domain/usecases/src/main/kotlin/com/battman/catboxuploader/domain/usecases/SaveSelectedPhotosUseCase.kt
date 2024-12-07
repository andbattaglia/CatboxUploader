package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.SaveSelectedPhotosUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class SaveSelectedPhotosUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
)
    : IUseCase<Params, Either<ErrorType, List<Photo>>>() {

    data class Params(
        val selectedPhotoIds: List<Photo>,
    )

    override suspend fun execute(params: Params) =
        photoRepository.cachePhotos(params.selectedPhotoIds)
}