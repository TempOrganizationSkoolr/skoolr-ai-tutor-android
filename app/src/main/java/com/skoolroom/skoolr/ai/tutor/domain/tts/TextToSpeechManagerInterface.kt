package com.skoolroom.skoolr.ai.tutor.domain.tts

import com.skoolroom.skoolr.ai.tutor.domain.model.SupportedLanguage
import kotlinx.coroutines.flow.StateFlow

interface TextToSpeechManagerInterface {
    val isSpeaking: StateFlow<Boolean>
    fun speak(text: String, language: SupportedLanguage = SupportedLanguage.ENGLISH)
    fun stop()
    fun shutdown()
}