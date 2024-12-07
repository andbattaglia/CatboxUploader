package com.battman.catboxuploader.feature.managephotos.ui

import com.battman.catboxuploader.domain.models.Photo
import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface ManagePhotosContract {

    sealed class UiIntent : MVIIntent {
        data object OnUploadClick : UiIntent()
        data class OnEditClick(val photoId: Long) : UiIntent()
        data class OnDeleteClick(val photoId: Long) : UiIntent()
    }

    sealed class UiState : MVIState {
        data object Loading : UiState()
        data class Success(
            val photos: List<Photo>,
        ) : UiState()
        data class Error(
            val titleRes: Int,
            val descriptionRes: Int,
        ) : UiState()
    }

    sealed class UiEvent : MVIEvent
}
