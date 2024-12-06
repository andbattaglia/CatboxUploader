package com.battman.catboxuploader.feature.createalbum.ui

import com.battman.catboxuploader.feature.createalbum.ui.CreateAlbumContract.UiEvent
import com.battman.catboxuploader.feature.createalbum.ui.CreateAlbumContract.UiIntent
import com.battman.catboxuploader.feature.createalbum.ui.CreateAlbumContract.UiState
import com.battman.core.ui.mvi.MVIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CreateAlbumModel @Inject constructor() :
    MVIModel<UiState, UiIntent, UiEvent>() {

    override fun createInitialState() = UiState.Loading

    override fun handleIntent(intent: UiIntent) {}

    companion object {
        private val TAG = CreateAlbumModel::class.simpleName
    }
}
