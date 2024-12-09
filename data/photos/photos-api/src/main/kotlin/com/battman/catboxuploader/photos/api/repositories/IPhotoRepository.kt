package com.battman.catboxuploader.photos.api.repositories

import arrow.core.Either
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.models.UploadDigest
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a repository for accessing media store operations.
 */
interface IPhotoRepository {

    /**
     * Retrieves a list of all photos available in the device media store.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: A list of `Photo` objects if the operation succeeds.
     */
    suspend fun getDevicePhotos(): Either<ErrorType, List<Photo>>

    /**
     * Caches a list of photos locally.
     *
     * @param photos The list of `Photo` objects to be cached.
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: The list of cached photos if the operation succeeds.
     */
    suspend fun cachePhotos(photos: List<Photo>): Either<ErrorType, List<Photo>>

    /**
     * Retrieves the list of locally cached photos.
     *
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: The list of cached photos if the operation succeeds.
     */
    suspend fun getCachedPhotos(): Either<ErrorType, List<Photo>>

    /**
     * Edits a cached photo's content by its unique identifier.
     *
     * @param photoId The unique identifier of the photo to be edited in the cache.
     * @param photoUri The new URI of the photo to update in the cache.
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: The updated list of cached photos if the operation succeeds.
     */
    suspend fun editCachedPhoto(photoId: Long, photoUri: String): Either<ErrorType, List<Photo>>

    /**
     * Deletes a cached photo by its unique identifier.
     *
     * @param photoId The unique identifier of the photo to be deleted from the cache.
     * @return An `Either` type containing:
     *         - `ErrorType`: An error descriptor if the operation fails.
     *         - `List<Photo>`: The updated list of cached photos if the operation succeeds.
     */
    suspend fun deleteCachedPhoto(photoId: Long): Either<ErrorType, List<Photo>>


    /**
     * Uploads the album and provides a stream of updates on the upload progress.
     *
     * @return A `Flow` emitting `UploadDigest` objects that represent the progress or status of the upload operation.
     *         - The flow emits intermediate updates during the upload process.
     *         - The final emission indicates the completion of the upload.
     */
    fun upload(): Flow<UploadDigest>
}