package com.battman.feature.select.photo.ui.models

import android.net.Uri
import com.battman.catboxuploader.domain.models.Photo
import java.util.Date

/**
 * Represents a UI model for a photo, typically used in presentation layers.
 *
 * @property id The unique identifier of the photo.
 * @property name The name of the photo.
 * @property data The date associated with the photo.
 * @property contentUri The content URI pointing to the photo's location.
 * @property selected Indicates whether the photo is selected.
 */
data class UIPhoto(
    val id: Long,
    val name: String,
    val data: Date,
    val contentUri: Uri,
    val selected: Boolean,
)

/**
 * Converts a list of `Photo` objects to a list of `UIPhoto` objects.
 *
 * @return A list of `UIPhoto` objects created from the source `Photo` objects.
 */
internal fun List<Photo>.toUIPhotos() = map { it.toUIPhoto() }

/**
 * Converts a `Photo` object to a `UIPhoto` object.
 *
 * @return A `UIPhoto` object created from the source `Photo` object.
 */
internal fun Photo.toUIPhoto() = UIPhoto(
    id = id,
    name = name,
    data = data,
    contentUri = Uri.parse(contentUri),
    selected = false,
)

/**
 * Converts a list of `UIPhoto` objects to a list of `Photo` objects.
 *
 * @return A list of `Photo` objects created from the source `UIPhoto` objects.
 */
internal fun List<UIPhoto>.toPhotos() = map { it.toPhoto() }

/**
 * Converts a `UIPhoto` object to a `Photo` object.
 *
 * @return A `Photo` object created from the source `UIPhoto` object.
 */
internal fun UIPhoto.toPhoto() = Photo(
    id = id,
    name = name,
    data = data,
    contentUri = contentUri.toString(),
)
