package com.battman.feature.select.photo.ui

import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface SelectPhotosContract {

    sealed class UiIntent : MVIIntent

    data object UiState : MVIState

    sealed class UiEvent : MVIEvent
}
