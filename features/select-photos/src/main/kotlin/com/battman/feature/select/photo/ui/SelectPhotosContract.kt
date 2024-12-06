package com.battman.feature.select.photo.ui

import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState
import com.battman.feature.select.photo.ui.models.UIPhoto

internal sealed interface SelectPhotosContract {

    sealed class UiIntent : MVIIntent {
        data object OnPermissionGranted : UiIntent()
        data class OnItemSelected(val photoId: Long) : UiIntent()
        data object OnNext : UiIntent()
    }

    sealed class UiState : MVIState {
        data object Loading : UiState()
        data class Success(
            val photos: List<UIPhoto>,
            val nextButtonEnabled: Boolean,
        ) : UiState()
        data class Error(val titleRes: Int, val descriptionRes: Int) : UiState()
    }

    sealed class UiEvent : MVIEvent {
        data object NavigateToUpload : UiEvent()
    }
}
