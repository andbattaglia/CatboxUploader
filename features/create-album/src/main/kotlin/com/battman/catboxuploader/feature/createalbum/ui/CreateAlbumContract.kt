package com.battman.catboxuploader.feature.createalbum.ui

import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface CreateAlbumContract {

    sealed class UiIntent : MVIIntent

    sealed class UiState : MVIState {
        data object Loading : UiState()
    }

    sealed class UiEvent : MVIEvent
}
