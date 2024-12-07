package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class GetSelectedPhotoByIdUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
)
    : IUseCase<GetSelectedPhotoByIdUseCase.Params, Either<ErrorType, Photo?>>() {

    data class Params(val photoId: Long)

    override suspend fun execute(params: Params) =
        photoRepository.getCachedPhotos()
            .map { photos -> photos.firstOrNull { it.id == params.photoId } }
}