package com.battman.feature.select.country.ui

import com.battman.catboxuploader.domain.models.Country
import com.battman.core.ui.mvi.MVIEvent
import com.battman.core.ui.mvi.MVIIntent
import com.battman.core.ui.mvi.MVIState

internal sealed interface SelectCountryContract {

    sealed class UiIntent : MVIIntent {
        data class OnCountryClick(val iso: Int) : UiIntent()
    }

    data class UiState(
        val countries: List<Country>,
    ) : MVIState

    sealed class UiEvent : MVIEvent {
        data object NavigateToGallery : UiEvent()
    }
}
