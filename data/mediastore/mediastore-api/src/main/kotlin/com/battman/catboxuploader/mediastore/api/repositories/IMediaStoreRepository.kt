package com.battman.catboxuploader.mediastore.api.repositories

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo

/**
 * Interface representing a repository for accessing media store operations.
 */
interface IMediaStoreRepository {

    /**
     * Retrieves a list of all photos available in the media store.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: A list of `Photo` objects if the operation succeeds.
     */
    suspend fun getAllPhotos(): Either<ErrorType, List<Photo>>
}