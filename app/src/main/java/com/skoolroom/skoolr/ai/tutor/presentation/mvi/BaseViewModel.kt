package com.skoolroom.skoolr.ai.tutor.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<EVENT : UiEvent, STATE : UiState, EFFECT : UiEffect>(
    initialState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effect = Channel<EFFECT>()
    val effect = _effect.receiveAsFlow()

    /**
     * Update the current State by applying the reducer.
     */
    protected fun updateState(reducer: STATE.() -> STATE) {
        _state.value = _state.value.reducer()
    }

    /**
     * Send a one-time Effect.
     */
    protected fun setEffect(builder: () -> EFFECT) {
        viewModelScope.launch {
            _effect.send(builder())
        }
    }

    /**
     * Handle UI Events.
     */
    abstract fun handleEvent(event: EVENT)

    /**
     * Entry point for the Composable to send Events.
     */
    fun setEvent(event: EVENT) {
        handleEvent(event)
    }
}