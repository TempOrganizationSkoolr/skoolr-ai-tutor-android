package com.skoolroom.skoolr.ai.tutor.domain.speech

import com.skoolroom.skoolr.ai.tutor.domain.model.RecognitionResult
import com.skoolroom.skoolr.ai.tutor.domain.model.SupportedLanguage
import kotlinx.coroutines.flow.Flow

interface SpeechRecognizerManagerInterface {
    fun startListening(language: SupportedLanguage): Flow<RecognitionResult>
    fun stopListening()
    fun destroy()
}