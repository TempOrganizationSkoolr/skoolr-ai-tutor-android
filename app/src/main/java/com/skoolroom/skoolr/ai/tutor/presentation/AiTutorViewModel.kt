package com.skoolroom.skoolr.ai.tutor.presentation

import androidx.lifecycle.viewModelScope
import com.skoolroom.skoolr.ai.tutor.domain.ai.AiRepository
import com.skoolroom.skoolr.ai.tutor.domain.model.RecognitionResult
import com.skoolroom.skoolr.ai.tutor.domain.speech.SpeechRecognizerManagerInterface
import com.skoolroom.skoolr.ai.tutor.domain.tts.TextToSpeechManagerInterface
import com.skoolroom.skoolr.ai.tutor.presentation.mvi.BaseViewModel
import com.skoolroom.skoolr.ai.tutor.util.cleanMarkdown
import kotlinx.coroutines.launch

class AiTutorViewModel(
    private val aiRepository: AiRepository,
    private val ttsManager: TextToSpeechManagerInterface,
    private val speechRecognizerManager: SpeechRecognizerManagerInterface
) : BaseViewModel<AiTutorContract.Event, AiTutorContract.State, AiTutorContract.Effect>(
    AiTutorContract.State()
) {

    init {
        viewModelScope.launch {
            ttsManager.isSpeaking.collect { isSpeaking ->
                updateState { copy(isSpeaking = isSpeaking) }
            }
        }
    }

    override fun handleEvent(event: AiTutorContract.Event) {
        when (event) {
            is AiTutorContract.Event.StartRecognition -> startRecognition()
            is AiTutorContract.Event.StopRecognition -> stopRecognition()
            is AiTutorContract.Event.OnPermissionResult -> {
                if (event.granted) {
                    startRecognition()
                } else {
                    setEffect { AiTutorContract.Effect.ShowMessage("Microphone permission denied.") }
                }
            }
            is AiTutorContract.Event.OnSelectRecognitionLanguage -> {
                updateState { copy(selectedLanguage = event.language) }
            }
        }
    }

    private fun startRecognition() {
        updateState {
            copy(
                isListening = true,
                recognizedText = "Listening...",
                aiResponseText = ""
            )
        }

        viewModelScope.launch {
            speechRecognizerManager.startListening(state.value.selectedLanguage).collect { result ->
                when (result) {
                    is RecognitionResult.State -> updateState { copy(recognizedText = result.message) }
                    is RecognitionResult.Result -> {
                        updateState { copy(recognizedText = result.text, isListening = false) }
                        fetchAiResponse(result.text)
                    }
                    is RecognitionResult.Error -> {
                        updateState { copy(recognizedText = result.error, isListening = false) }
                        setEffect { AiTutorContract.Effect.ShowMessage(result.error) }
                    }
                }
            }
        }
    }

    private fun stopRecognition() {
        speechRecognizerManager.stopListening()
        updateState { copy(isListening = false) }
    }

    private fun fetchAiResponse(prompt: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = aiRepository.getResponse(prompt)
            val cleaned = result.cleanMarkdown()
            updateState { copy(aiResponseText = cleaned) }
            ttsManager.speak(cleaned, state.value.selectedLanguage)
            updateState { copy(isLoading = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
        speechRecognizerManager.destroy()
    }
}

