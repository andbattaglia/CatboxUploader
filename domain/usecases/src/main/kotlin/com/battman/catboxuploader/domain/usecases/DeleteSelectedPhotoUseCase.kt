package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.DeleteSelectedPhotoUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import kotlinx.coroutines.processNextEventInCurrentThread
import javax.inject.Inject

class DeleteSelectedPhotoUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
)
    : IUseCase<Params, Either<ErrorType, List<Photo>>>() {

    data class Params(val photoID: Long)

    override suspend fun execute(params: Params) =
        photoRepository.deleteCachedPhoto(params.photoID)
}