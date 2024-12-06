package com.battman.catboxuploader.domain.usecases

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.usecases.GetAllPhotosUseCase.Params
import com.battman.catboxuploader.mediastore.api.repositories.IMediaStoreRepository
import com.battman.core.usecase.api.IUseCase
import javax.inject.Inject

class GetAllPhotosUseCase @Inject constructor(
    private val mediastoreRepository: IMediaStoreRepository,
) : IUseCase<Params, Either<ErrorType, List<Photo>>>() {

    object Params

    override suspend fun execute(params: Params) =
        mediastoreRepository.getAllPhotos()
}
