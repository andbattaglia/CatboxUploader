package com.battman.catboxuploader.photos.impl.models

import android.net.Uri
import com.battman.catboxuploader.domain.models.Photo
import java.util.Date

data class PhotoEntity(
    val id: Long,
    val name: String,
    val contentUri: Uri,
    val data: Date,
)

internal fun PhotoEntity.toDomain() =
    Photo(
        id = id,
        name = name,
        contentUri = contentUri.toString(),
        data = data,
    )
