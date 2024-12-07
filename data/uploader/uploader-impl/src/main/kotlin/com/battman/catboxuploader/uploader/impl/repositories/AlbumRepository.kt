package com.battman.catboxuploader.uploader.impl.repositories

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.battman.catboxuploader.domain.models.Album
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.uploader.api.repositories.IAlbumRepository
import com.battman.catboxuploader.uploader.impl.datasource.CatboxApiContract
import com.battman.core.dispatcher.api.IoDispatcher
import com.battman.data.network.extensions.toErrorType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val catboxApiContract: CatboxApiContract,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IAlbumRepository {

    private var currentAlbum: Album? = null

    override suspend fun addPhotos(photos: List<Photo>): Either<ErrorType, Album> {
        currentAlbum = currentAlbum?.copy(photos = photos) ?: Album(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            userHash = "CatboxUploaderAlbum",
            photos = photos
        ).also { currentAlbum = it }

        return currentAlbum?.right() ?: ErrorType.Unknown().left()
    }

    override suspend fun getAlbum(): Either<ErrorType, Album> =
        currentAlbum?.right() ?: ErrorType.Unknown().left()

    override suspend fun upload(): Either<ErrorType, String> =
        withContext(ioDispatcher){
            currentAlbum?.let { album ->
                val contentResolver: ContentResolver = context.contentResolver

                val reqType = "fileupload".toRequestBody("text/plain".toMediaTypeOrNull())
                val userHashBody = ***REMOVED***.toRequestBody("text/plain".toMediaTypeOrNull())

                val fileUri = Uri.parse(album.photos.first().contentUri)
                val fileName = contentResolver.getFileName(fileUri)
                val inputStream = contentResolver.openInputStream(fileUri)
                val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), inputStream!!.readBytes())
                val fileToUpload = MultipartBody.Part.createFormData("fileToUpload", fileName, requestBody)

                catboxApiContract.createAlbum(reqType, userHashBody, fileToUpload)
                    .mapLeft { error ->
                        error.toErrorType()
                    }
            } ?: ErrorType.Unknown().left()
        }

    private fun ContentResolver.getFileName(uri: Uri): String? {
        val returnCursor = query(uri, null, null, null, null) ?: return null
        val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val fileName = returnCursor.getString(nameIndex)
        returnCursor.close()
        return fileName
    }
}

