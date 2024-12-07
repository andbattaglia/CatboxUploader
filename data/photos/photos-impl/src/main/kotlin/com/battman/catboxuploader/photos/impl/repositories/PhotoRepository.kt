package com.battman.catboxuploader.photos.impl.repositories

import arrow.core.Either
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.photos.api.repositories.IPhotoRepository
import com.battman.catboxuploader.photos.impl.datasource.ContentResolverDatasource
import com.battman.catboxuploader.photos.impl.models.toDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val contentResolverDatasource: ContentResolverDatasource,
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

    override suspend fun deleteCachedPhoto(photoId: Long): Either<ErrorType, List<Photo>> {
        cache.remove(photoId)
        return cache.values.toList().right()
    }

//    override suspend fun upload(): Either<ErrorType, String> =
//        withContext(ioDispatcher){
//            currentAlbum?.let { album ->
//                val contentResolver: ContentResolver = context.contentResolver
//
//                val reqType = "fileupload".toRequestBody("text/plain".toMediaTypeOrNull())
//                val userHashBody = ***REMOVED***.toRequestBody("text/plain".toMediaTypeOrNull())
//
//                val fileUri = Uri.parse(album.photos.first().contentUri)
//                val fileName = contentResolver.getFileName(fileUri)
//                val inputStream = contentResolver.openInputStream(fileUri)
//                val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), inputStream!!.readBytes())
//                val fileToUpload = MultipartBody.Part.createFormData("fileToUpload", fileName, requestBody)
//
//                catboxApiContract.createAlbum(reqType, userHashBody, fileToUpload)
//                    .mapLeft { error ->
//                        error.toErrorType()
//                    }
//            } ?: ErrorType.Unknown().left()
//        }
//
//    private fun ContentResolver.getFileName(uri: Uri): String? {
//        val returnCursor = query(uri, null, null, null, null) ?: return null
//        val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
//        returnCursor.moveToFirst()
//        val fileName = returnCursor.getString(nameIndex)
//        returnCursor.close()
//        return fileName
//    }
}
