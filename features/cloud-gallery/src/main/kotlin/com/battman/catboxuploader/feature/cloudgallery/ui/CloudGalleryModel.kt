package com.battman.catboxuploader.feature.cloudgallery.ui

import androidx.lifecycle.viewModelScope
import com.battman.catboxuploader.domain.usecases.GetSelectedPhotosUseCase
import com.battman.catboxuploader.feature.cloudgallery.ui.CloudGalleryContract.UiEvent
import com.battman.catboxuploader.feature.cloudgallery.ui.CloudGalleryContract.UiIntent
import com.battman.catboxuploader.feature.cloudgallery.ui.CloudGalleryContract.UiState
import com.battman.core.ui.mvi.MVIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CloudGalleryModel @Inject constructor(
    private val getSelectedPhotosUseCase: GetSelectedPhotosUseCase,
) : MVIModel<UiState, UiIntent, UiEvent>() {

    override fun createInitialState() = UiState.Loading

    override fun handleIntent(intent: UiIntent) {}

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() {
        viewModelScope.launch {
            getSelectedPhotosUseCase.execute(params = GetSelectedPhotosUseCase.Params)
                .fold(
                    ifRight = { photos ->
                        setState { UiState.Success(photos) }
                    },
                    ifLeft = {},
                )
        }
    }

    companion object {
        private val TAG = CloudGalleryModel::class.simpleName
    }
}
