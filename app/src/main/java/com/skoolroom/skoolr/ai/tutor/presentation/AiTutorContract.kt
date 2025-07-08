package com.skoolroom.skoolr.ai.tutor.presentation

import com.skoolroom.skoolr.ai.tutor.domain.model.SupportedLanguage
import com.skoolroom.skoolr.ai.tutor.presentation.mvi.UiEffect
import com.skoolroom.skoolr.ai.tutor.presentation.mvi.UiEvent
import com.skoolroom.skoolr.ai.tutor.presentation.mvi.UiState

interface AiTutorContract {

    sealed interface Event : UiEvent {
        data object StartRecognition : Event
        data object StopRecognition : Event
        data class OnPermissionResult(val granted: Boolean) : Event
        data class OnSelectRecognitionLanguage(val language: SupportedLanguage) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowMessage(val message: String) : Effect
    }

    data class State(
        val recognizedText: String = "Press the button and start speaking",
        val aiResponseText: String = "",
        val isLoading: Boolean = false,
        val isListening: Boolean = false,
        val isSpeaking: Boolean = false,
        val selectedLanguage: SupportedLanguage = SupportedLanguage.ENGLISH,
    ) : UiState
}