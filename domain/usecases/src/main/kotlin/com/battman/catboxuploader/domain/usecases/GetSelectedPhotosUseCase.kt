package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.GetSelectedPhotosUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class GetSelectedPhotosUseCase @Inject constructor(
    private val photoRegex: IPhotoRepository,
)
    : IUseCase<Params, Either<ErrorType, List<Photo>>>() {

    data object Params

    override suspend fun execute(params: Params) =
        photoRegex.getCachedPhotos()
}