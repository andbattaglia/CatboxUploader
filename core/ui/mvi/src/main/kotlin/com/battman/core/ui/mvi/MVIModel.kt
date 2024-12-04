package com.battman.core.ui.mvi

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Interface for events that can be triggered in the UI layer.
 */
interface MVIEvent

/**
 * Interface for intents that represent user actions in the UI layer.
 */
interface MVIIntent

/**
 * Interface for states that represent the current UI state.
 */
interface MVIState

/**
 * Type alias for the intent dispatcher function.
 * It is responsible for dispatching UI intents to the appropriate components.
 */
typealias IntentDispatcher<I> = (I) -> Unit

/**
 * Abstract base class for ViewModels in the UI layer.
 *
 * @param S The type of UI state.
 * @param I The type of UI intent.
 * @param E The type of UI event.
 */
abstract class MVIModel<S : MVIState, I : MVIIntent, E : MVIEvent> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }
    abstract fun createInitialState(): S

    private val _uiEvent = Channel<E>(Channel.BUFFERED)
    private val _uiIntent = MutableSharedFlow<I>()
    private val _uiState = MutableStateFlow(initialState)

    @Composable
    operator fun component1(): S = _uiState.collectAsStateWithLifecycle().value
    operator fun component2(): Flow<E> = _uiEvent.receiveAsFlow()
    operator fun component3(): IntentDispatcher<I> = { _uiIntent.tryEmit(it) }

    init {
        observeIntent()
    }

    val currentState: S
        get() = _uiState.value

    protected fun setState(reduce: S.() -> S) {
        val newState = _uiState.value.reduce()
        _uiState.value = newState
    }

    protected fun sendEvent(builder: () -> E) {
        val effectValue = builder()
        _uiEvent.trySend(effectValue)
    }

    private fun observeIntent() {
        viewModelScope.launch {
            _uiIntent.collect {
                handleIntent(it)
            }
        }
    }

    abstract fun handleIntent(intent: I)
}
