package com.battman.catboxuploader.photos.impl.datasource

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.photos.impl.models.PhotoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContentResolverDatasource @Inject constructor(
    private val contentResolver: ContentResolver,
) {
    suspend fun getMediaStoreImages(): Either<ErrorType, List<PhotoEntity>> {
        try {
            return withContext(Dispatchers.IO) {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED,
                )

                val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                val photos = mutableListOf<PhotoEntity>()

                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder,
                )?.use { cursor ->

                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val dateModifiedColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                    val displayNameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val dateModified =
                            Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
                        val displayName = cursor.getString(displayNameColumn)

                        val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )

                        photos.add(
                            PhotoEntity(
                                id = id,
                                name = displayName,
                                contentUri = contentUri,
                                data = dateModified,
                            ),
                        )
                    }
                }
                return@withContext photos.right()
            }
        } catch (e: Exception) {
            return ErrorType.Unknown("Error while fetching photos from media store").left()
        }
    }
}
