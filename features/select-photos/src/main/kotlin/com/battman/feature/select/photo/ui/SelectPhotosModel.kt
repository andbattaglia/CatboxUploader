package com.battman.feature.select.photo.ui

import com.battman.core.ui.mvi.MVIModel
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiEvent
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SelectPhotosModel @Inject constructor() : MVIModel<UiState, UiIntent, UiEvent>() {

    override fun createInitialState() = UiState

    override fun handleIntent(intent: UiIntent) { }
}
