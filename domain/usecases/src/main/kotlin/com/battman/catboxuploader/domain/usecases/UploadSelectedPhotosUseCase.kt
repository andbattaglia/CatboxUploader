package com.battman.catboxuploader.domain.usecases

import com.battman.catboxuploader.domain.models.UploadDigest
import com.battman.catboxuploader.domain.usecases.UploadSelectedPhotosUseCase.Params
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.core.usecase.api.IFlowUseCase
import javax.inject.Inject

class UploadSelectedPhotosUseCase @Inject constructor(
    private val photoRepository: IPhotoRepository,
) : IFlowUseCase<Params, UploadDigest>() {

    data object Params

    override fun execute(params: Params) = photoRepository.upload()
}