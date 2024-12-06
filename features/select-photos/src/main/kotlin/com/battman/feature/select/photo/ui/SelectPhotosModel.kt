package com.battman.feature.select.photo.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.battman.catboxuploader.domain.usecases.GetAllPhotosUseCase
import com.battman.catboxuploader.domain.usecases.SendSelectedPhotosToUploadUseCase
import com.battman.catboxuploader.domain.usecases.SendSelectedPhotosToUploadUseCase.Params
import com.battman.core.ui.mvi.MVIModel
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiEvent
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiEvent.NavigateToUpload
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnItemSelected
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnNext
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiIntent.OnPermissionGranted
import com.battman.feature.select.photo.ui.SelectPhotosContract.UiState
import com.battman.feature.select.photo.ui.models.UIPhoto
import com.battman.feature.select.photo.ui.models.toPhotos
import com.battman.feature.select.photo.ui.models.toUIPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SelectPhotosModel @Inject constructor(
    private val getAllPhotosUseCase: GetAllPhotosUseCase,
    private val sendSelectedPhotosToUploadUseCase: SendSelectedPhotosToUploadUseCase,
) : MVIModel<UiState, UiIntent, UiEvent>() {

    private val selectedIds = mutableListOf<Long>()

    override fun createInitialState() =
        UiState(
            photos = emptyList(),
            nextButtonEnabled = false,
        )

    override fun handleIntent(intent: UiIntent) =
        when (intent) {
            OnPermissionGranted -> {
                fetchPhotos()
            }
            is OnItemSelected -> {
                selectPhoto(intent.photoId)
            }
            OnNext -> {
                sendSelectedPhotos()
            }
        }

    private fun fetchPhotos() {
        viewModelScope.launch {
            getAllPhotosUseCase.execute(params = GetAllPhotosUseCase.Params)
                .fold(
                    ifRight = { photos ->
                        updateSelectedPhotos(photos.toUIPhotos())
                    },
                    ifLeft = {},
                )
        }
    }

    private fun selectPhoto(selectedPhotoId: Long) {
        if (selectedIds.contains(selectedPhotoId)) {
            selectedIds.remove(selectedPhotoId)
        } else {
            selectedIds.add(selectedPhotoId)
        }
        updateSelectedPhotos(currentState.photos)
    }

    private fun updateSelectedPhotos(photos: List<UIPhoto>) {
        val result = photos.map {
            it.copy(selected = it.id in selectedIds)
        }
        setState { copy(photos = result, nextButtonEnabled = result.any { it.selected }) }
    }

    private fun sendSelectedPhotos() {
        viewModelScope.launch {
            val selectedPhotos = currentState.photos.filter { it.selected }.toPhotos()
            sendSelectedPhotosToUploadUseCase.execute(Params(selectedPhotos))
                .fold(
                    ifRight = { sendEvent { NavigateToUpload } },
                    ifLeft = { Log.e(TAG, "Failed to send selected photos") },
                )
        }
    }

    companion object {
        private val TAG = SelectPhotosModel::class.simpleName
    }
}
