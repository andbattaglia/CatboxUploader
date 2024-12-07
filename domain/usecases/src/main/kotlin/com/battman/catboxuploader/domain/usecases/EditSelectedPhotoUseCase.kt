package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class EditSelectedPhotoUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
) : IUseCase<EditSelectedPhotoUseCase.Params, Either<ErrorType, List<Photo>>>() {

    data class Params(val photoId: Long, val uri: String)

    override suspend fun execute(params: Params) =
        photoRepository.editCachedPhoto(params.photoId, params.uri)
}