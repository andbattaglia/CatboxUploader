package com.battman.feature.select.country.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.battman.catboxuploader.domain.usecases.GetCountriesUseCase
import com.battman.catboxuploader.domain.usecases.GetCountriesUseCase.Params
import com.battman.core.ui.mvi.MVIModel
import com.battman.feature.select.country.ui.SelectCountryContract.UiEvent
import com.battman.feature.select.country.ui.SelectCountryContract.UiEvent.NavigateToGallery
import com.battman.feature.select.country.ui.SelectCountryContract.UiIntent
import com.battman.feature.select.country.ui.SelectCountryContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SelectCountryModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
) : MVIModel<UiState, UiIntent, UiEvent>() {

    override fun createInitialState() =
        UiState(countries = emptyList())

    override fun handleIntent(intent: UiIntent) = when (intent) {
        is UiIntent.OnCountryClick -> performCountryClick(intent.iso)
    }

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUseCase.execute(params = Params)
                .fold(
                    ifRight = { setState { copy(countries = it) } },
                    ifLeft = {},
                )
        }
    }

    private fun performCountryClick(iso: Int) {
        Log.d(TAG, "The country with iso $iso was clicked")
        sendEvent { NavigateToGallery }
    }

    companion object {
        private val TAG = SelectCountryModel::class.simpleName
    }
}
