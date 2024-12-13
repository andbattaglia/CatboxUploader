package com.battman.catboxuploader.feature.managephotos.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.battman.catboxuploader.domain.models.UploadDigest
import com.battman.catboxuploader.domain.usecases.DeleteSelectedPhotoUseCase
import com.battman.catboxuploader.domain.usecases.EditSelectedPhotoUseCase
import com.battman.catboxuploader.domain.usecases.GetSelectedPhotoByIdUseCase
import com.battman.catboxuploader.domain.usecases.GetSelectedPhotosUseCase
import com.battman.catboxuploader.domain.usecases.UploadSelectedPhotosUseCase
import com.battman.catboxuploader.feature.common.extensions.toMessageDescription
import com.battman.catboxuploader.feature.common.extensions.toMessageTitle
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiEvent
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnDeleteClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnEditClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnRefresh
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnStopUploadClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiIntent.OnUploadClick
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiState
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiState.EditMode
import com.battman.catboxuploader.feature.managephotos.ui.ManagePhotosContract.UiState.Loading
import com.battman.core.dispatcher.api.MainDispatcher
import com.battman.core.ui.mvi.MVIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ManagePhotosModel @Inject constructor(
    private val getSelectedPhotosUseCase: GetSelectedPhotosUseCase,
    private val getSelectedPhotoByIdUseCase: GetSelectedPhotoByIdUseCase,
    private val editSelectedPhotoUseCase: EditSelectedPhotoUseCase,
    private val deleteSelectedPhotoUseCase: DeleteSelectedPhotoUseCase,
    private val uploadSelectedPhotosUseCase: UploadSelectedPhotosUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : MVIModel<UiState, UiIntent, UiEvent>() {

    private var uploadJob: Job? = null

    override fun createInitialState() = Loading

    override fun handleIntent(intent: UiIntent) =
        when (intent) {
            is OnUploadClick -> {
                uploadPhotos()
            }
            is OnStopUploadClick -> {
                cancelUpload()
                fetchSelectedPhotos()
            }
            is OnEditClick -> {
                performEditClick(intent.photoId)
            }
            is UiIntent.OnEditSaveClick -> {
                performEditSaveClick(intent.photoId, intent.contentUri)
            }
            is OnDeleteClick -> {
                performDeleteClick(intent.photoId)
            }
            is OnRefresh -> {
                fetchSelectedPhotos()
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

    private fun performEditClick(photoId: Long) {
        viewModelScope.launch {
            getSelectedPhotoByIdUseCase.execute(GetSelectedPhotoByIdUseCase.Params(photoId))
                .fold(
                    ifRight = { photo ->
                        photo?.let { setState { EditMode(it) } }
                    },
                    ifLeft = { error ->
                        Log.e(TAG, "performEditClick: $error")
                    },
                )
        }
    }

    private fun performEditSaveClick(photoId: Long, contentUri: String) {
        Log.d(TAG, "performEditSaveClick: $photoId, $contentUri")
        viewModelScope.launch {
            editSelectedPhotoUseCase.execute(EditSelectedPhotoUseCase.Params(photoId, contentUri))
                .fold(
                    ifRight = { photos ->
                        setState {
                            UiState.Success(photos)
                        }
                    },
                    ifLeft = { error ->
                        Log.e(TAG, "performEditSaveClick: $error")
                    },
                )
        }
    }

    private fun performDeleteClick(photoId: Long) {
        viewModelScope.launch {
            deleteSelectedPhotoUseCase.execute(DeleteSelectedPhotoUseCase.Params(photoId))
                .fold(
                    ifRight = { photos ->
                        setState {
                            UiState.Success(photos)
                        }
                    },
                    ifLeft = { error ->
                        Log.e(TAG, "performDeleteClick: $error")
                    },
                )
        }
    }

    private fun cancelUpload() {
        uploadJob?.cancel()
    }

    private fun uploadPhotos() {
        uploadJob = viewModelScope.launch {
            uploadSelectedPhotosUseCase.execute(UploadSelectedPhotosUseCase.Params)
                .flowOn(mainDispatcher)
                .onEach { uploadDigest ->
                    when (uploadDigest) {
                        is UploadDigest.Progress -> {
                            setState { UiState.UploadMode(index = uploadDigest.index, total = uploadDigest.total, progress = uploadDigest.progress) }
                        }
                        is UploadDigest.Success -> {
                            sendEvent { UiEvent.NavigateToResult }
                        }
                        is UploadDigest.Error -> {
                            setState {
                                UiState.Error(
                                    titleRes = uploadDigest.errorType.toMessageTitle(),
                                    descriptionRes = uploadDigest.errorType.toMessageDescription(),
                                )
                            }
                        }
                    }
                }
                .debounce(16)
                .collect()
        }
    }

    companion object {
        private val TAG = ManagePhotosModel::class.simpleName
    }
}
