package com.battman.catboxuploader.feature.cloudgallery.ui

import com.battman.catboxuploader.domain.models.Photo
import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface CloudGalleryContract {

    sealed class UiIntent : MVIIntent

    sealed class UiState : MVIState {
        data object Loading : UiState()
        data class Success(
            val photos: List<Photo>,
        ) : UiState()
        data class Error(val titleRes: Int, val descriptionRes: Int) : UiState()
    }

    sealed class UiEvent : MVIEvent
}
