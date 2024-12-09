package com.battman.catboxuploader.feature.managephotos.ui

import com.battman.catboxuploader.domain.models.Photo
import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface ManagePhotosContract {

    sealed class UiIntent : MVIIntent {
        data object OnUploadClick : UiIntent()
        data object OnStopUploadClick : UiIntent()
        data class OnEditClick(val photoId: Long) : UiIntent()
        data class OnEditSaveClick(val photoId: Long, val contentUri: String) : UiIntent()
        data class OnDeleteClick(val photoId: Long) : UiIntent()
        data object OnRefresh : UiIntent()
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
        data class EditMode(
            val photo: Photo,
        ) : UiState()
        data class UploadMode(
            val index: Int,
            val total: Int,
            val progress: Int,
        ) : UiState()
    }

    sealed class UiEvent : MVIEvent {
        data object NavigateToResult : UiEvent()
    }
}
