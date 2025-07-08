package com.skoolroom.skoolr.ai.tutor.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.skoolroom.skoolr.ai.tutor.domain.model.SupportedLanguage
import com.skoolroom.skoolr.ai.tutor.domain.tts.TextToSpeechManagerInterface
import com.skoolroom.skoolr.ai.tutor.util.cleanMarkdown
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

class TextToSpeechManager(context: Context): TextToSpeechManagerInterface {

    private var tts: TextToSpeech? = null
    private val _isSpeaking = MutableStateFlow(false)
    override val isSpeaking: StateFlow<Boolean> = _isSpeaking

 /*   init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                tts?.setPitch(1.1f)
                tts?.setSpeechRate(1.0f)
            }
        }
    }

    override fun speak(text: String) {
        tts?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "TTS_ID"
        )
    }*/

    init {
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) {
                Log.e("TTS", "Initialization failed")
            }
        }
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
            }

            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
            }

            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
            }
        })
    }

    override fun speak(text: String, language: SupportedLanguage) {
        tts?.language = language.ttsLocale
        tts?.setPitch(1.1f)
        tts?.setSpeechRate(1.0f)

        val cleaned = text.cleanMarkdown()
        _isSpeaking.value = true
        tts?.speak(
            cleaned,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "TTS_ID"
        )
    }

    override fun stop() {
        tts?.stop()
    }

    override fun shutdown() {
        tts?.shutdown()
    }
}