package com.battman.catboxuploader.photos.impl.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import arrow.core.Either
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.domain.models.UploadDigest
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.catboxuploader.photos.impl.datasource.CatboxApiContract
import com.battman.catboxuploader.photos.impl.datasource.ContentResolverDatasource
import com.battman.catboxuploader.photos.impl.models.toDomain
import com.battman.core.contentresolver.api.IContentResolverHelper
import com.battman.core.dispatcher.api.IoDispatcher
import com.battman.data.network.utils.ProgressRequestBody
import dagger.hilt.android.qualifiers.ApplicationContext
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val contentResolverDatasource: ContentResolverDatasource,
    private val catboxApiContract: CatboxApiContract,
    private val contentResolverHelper: IContentResolverHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IPhotoRepository {

    private val cache = mutableMapOf<Long, Photo>()

    override suspend fun getDevicePhotos(): Either<ErrorType, List<Photo>> =
        contentResolverDatasource.getMediaStoreImages()
            .map { photos -> photos.map { it.toDomain() } }

    override suspend fun cachePhotos(photos: List<Photo>): Either<ErrorType, List<Photo>> {
        cache.apply {
            clear()
            putAll(photos.associateBy(Photo::id))
        }
        return photos.right()
    }

    override suspend fun getCachedPhotos(): Either<ErrorType, List<Photo>> =
        cache.values.toList().right()

    override suspend fun editCachedPhoto(
        photoId: Long,
        photoUri: String,
    ): Either<ErrorType, List<Photo>> {
        cache[photoId]?.let { photo ->
            cache[photoId] = photo.copy(contentUri = photoUri)
        }
        return cache.values.toList().right()
    }

    override suspend fun deleteCachedPhoto(photoId: Long): Either<ErrorType, List<Photo>> {
        cache.remove(photoId)
        return cache.values.toList().right()
    }

    override fun upload() = flow {
        try {
            cache.values
                .forEachIndexed { index, photo ->
                    if (!photo.uploaded) {
                        uploadPhoto(
                            photo = photo,
                            progress = { progress ->
                                delay(16)
                                emit(UploadDigest.Progress(index = index, total = cache.values.size, progress = progress))
                            },
                            success = { url ->
                                Log.d("PhotoRepository", "Upload single photo success: $url")
                            },
                            error = {
                                emit(
                                    UploadDigest.Error(
                                        index = index,
                                        total = cache.values.size,
                                        errorType = it,
                                    ),
                                )
                            },
                        )
                    }
                }

            cache.values.forEach {
                Log.d("PhotoRepository", "Upload success: ${it.uploadedLink}")
                emit(UploadDigest.Success)
            }
        } catch (e: Exception) {
            emit(UploadDigest.Error(index = 0, total = 0, errorType = ErrorType.Unknown(e.message ?: "Unknown error")))
        }
    }.flowOn(ioDispatcher)

    private suspend fun uploadPhoto(
        photo: Photo,
        progress: suspend (progress: Int) -> Unit,
        success: suspend (url: String) -> Unit,
        error: suspend (error: ErrorType) -> Unit,
    ) {
        val reqType = "fileupload".toRequestBody("text/plain".toMediaTypeOrNull())
        val userHashBody = ***REMOVED***.toRequestBody("text/plain".toMediaTypeOrNull())

        val fileUri = Uri.parse(photo.contentUri)
        val fileName = contentResolverHelper.getFileName(fileUri)

        contentResolverHelper.uriToFile(fileUri)?.let { file ->
            val compressedImageFile = Compressor.compress(context, file)
            val progressBody = ProgressRequestBody(compressedImageFile, "image/jpeg")
            val progressFlow = progressBody.progressFlow()
            progressFlow.collect { progress ->
                progress(progress)
            }
            val fileToUpload = MultipartBody.Part.createFormData("fileToUpload", fileName, progressBody)

            val response = catboxApiContract.createAlbum(reqType, userHashBody, fileToUpload)
            if (response.isSuccessful) {
                response.body()?.let {
                    val result = it.string()
                    cache[photo.id]?.let { cachedPhoto ->
                        cache[photo.id] = cachedPhoto.copy(uploaded = true, uploadedLink = result)
                    }
                    success(result)
                } ?: error(ErrorType.Api.Network)
            } else {
                error(ErrorType.Api.Network)
            }
        } ?: error(ErrorType.Unknown("Failed to get file from URI"))
    }
}
