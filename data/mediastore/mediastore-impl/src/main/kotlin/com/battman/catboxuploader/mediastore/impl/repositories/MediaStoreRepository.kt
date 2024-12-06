package com.battman.catboxuploader.mediastore.impl.repositories

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.battman.catboxuploader.domain.models.ErrorType
import com.battman.catboxuploader.domain.models.Photo
import com.battman.catboxuploader.mediastore.api.repositories.IMediaStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : IMediaStoreRepository {

    override suspend fun getAllPhotos(): Either<ErrorType, List<Photo>> {
        try {
            return withContext(Dispatchers.IO) {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED,
                )

                val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                val photos = mutableListOf<Photo>()

                context.contentResolver.query(
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

                    Log.i("PHOTOS", "Found ${cursor.count} images")
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
                            Photo(
                                id = id,
                                name = displayName,
                                contentUri = contentUri.toString(),
                                data = dateModified,
                            ),
                        )
                    }
                }
                return@withContext photos.right()
            }
        } catch (e: Exception) {
            return ErrorType.Unknown("Error while fetching photos").left()
        }
    }

    @Suppress("SameParameterValue")
    @SuppressLint("SimpleDateFormat")
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }
}
