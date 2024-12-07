package com.battman.catboxuploader.feature.managephotos.ui

import androidx.lifecycle.viewModelScope
import com.battman.catboxuploader.domain.usecases.DeleteSelectedPhotoUseCase
import com.battman.catboxuploader.domain.usecases.GetSelectedPhotosUseCase
import com.battman.catboxuploader.domain.usecases.UploadSelectedPhotosUseCase
import com.battman.catboxuploader.feature.common.extensions.toMessageDescription
import com.battman.catboxuploader.feature.common.extensions.toMessageTitle
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiEvent
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnDeleteClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnEditClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnUploadClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiState
import com.battman.core.ui.mvi.MVIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManagePhotosModel @Inject constructor(
    private val getSelectedPhotosUseCase: GetSelectedPhotosUseCase,
    private val deleteSelectedPhotoUseCase: DeleteSelectedPhotoUseCase,
    private val uploadSelectedPhotosUseCase: UploadSelectedPhotosUseCase,
) :
    MVIModel<UiState, UiIntent, UiEvent>() {

    override fun createInitialState() = UiState.Loading

    override fun handleIntent(intent: UiIntent) =
        when (intent) {
            is OnUploadClick -> {
                uploadPhotos()
            }
            is OnEditClick -> {
            }
            is OnDeleteClick -> {
                deletePhoto(intent.photoId)
            }
        }

    init {
        fetchSelectedPhotos()
    }

    private fun fetchSelectedPhotos() {
        viewModelScope.launch {
            getSelectedPhotosUseCase.execute(GetSelectedPhotosUseCase.Params)
                .fold(
                    ifRight = { photos ->
                        setState {
                            UiState.Success(photos)
                        }
                    },
                    ifLeft = { error ->
                        setState {
                            UiState.Error(
                                titleRes = error.toMessageTitle(),
                                descriptionRes = error.toMessageDescription(),
                            )
                        }
                    },
                )
        }
    }

    private fun deletePhoto(photoId: Long) {
        viewModelScope.launch {
            deleteSelectedPhotoUseCase.execute(DeleteSelectedPhotoUseCase.Params(photoId))
                .fold(
                    ifRight = { photos ->
                        setState {
                            UiState.Success(photos)
                        }
                    },
                    ifLeft = { error ->
                        setState {
                            UiState.Error(
                                titleRes = error.toMessageTitle(),
                                descriptionRes = error.toMessageDescription(),
                            )
                        }
                    },
                )
        }
    }

    private fun uploadPhotos() {
        viewModelScope.launch {
            uploadSelectedPhotosUseCase.execute(UploadSelectedPhotosUseCase.Params)
                .fold(
                    ifRight = { albumId -> },
                    ifLeft = { error -> },
                )
        }
    }

    companion object {
        private val TAG = ManagePhotosModel::class.simpleName
    }
}
