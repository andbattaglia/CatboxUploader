package com.battman.feature.select.country.ui

import com.battman.catboxuploader.domain.models.Country
import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface SelectCountryContract {

    sealed class UiIntent : MVIIntent {
        data class OnCountryClick(val iso: Int) : UiIntent()
    }

    sealed class UiState : MVIState {
        data object Loading : UiState()
        data class Success(
            val countries: List<Country>,
        ) : UiState()
        data class Error(val titleRes: Int, val descriptionRes: Int) : UiState()
    }

    sealed class UiEvent : MVIEvent {
        data object NavigateToGallery : UiEvent()
    }
}
