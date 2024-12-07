package com.battman.catboxuploader.uploader.api.repositories

import arrow.core.Either
import com.battman.catboxuploader.domain.models.Album
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo

/**
 * Interface representing a repository for managing album-related operations.
 */
interface IAlbumRepository {

    /**
     * Retrieves the current album.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `Album`: The current album object if the operation succeeds.
     */
    suspend fun getAlbum(): Either<ErrorType, Album>

    /**
     * Uploads the album and returns the result.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `String`: A success message or identifier if the operation succeeds.
     */
    suspend fun upload(): Either<ErrorType, String>
}