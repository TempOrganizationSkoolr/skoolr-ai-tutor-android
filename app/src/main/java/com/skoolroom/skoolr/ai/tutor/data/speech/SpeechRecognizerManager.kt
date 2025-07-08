package com.skoolroom.skoolr.ai.tutor.data.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.skoolroom.skoolr.ai.tutor.domain.model.RecognitionResult
import com.skoolroom.skoolr.ai.tutor.domain.model.SupportedLanguage
import com.skoolroom.skoolr.ai.tutor.domain.speech.SpeechRecognizerManagerInterface
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SpeechRecognizerManager(private val context: Context): SpeechRecognizerManagerInterface {

    private var recognizer: SpeechRecognizer? = null

    init {
        recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    }

    override fun startListening(language: SupportedLanguage): Flow<RecognitionResult> = callbackFlow {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language.recognitionLocaleCode)
        }

        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                trySend(RecognitionResult.State("Ready to listen..."))
            }

            override fun onBeginningOfSpeech() {
                trySend(RecognitionResult.State("Beginning of speech detected"))
            }

            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                trySend(RecognitionResult.State("End of speech detected"))
            }

            override fun onError(error: Int) {
                trySend(RecognitionResult.Error("Error recognizing speech: $error"))
                close()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = if (!matches.isNullOrEmpty()) matches[0] else "No speech recognized."
                trySend(RecognitionResult.Result(text))
                close()
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        recognizer?.setRecognitionListener(listener)
        recognizer?.startListening(intent)

        awaitClose {
            recognizer?.stopListening()
        }
    }

    override fun stopListening() {
        recognizer?.stopListening()
    }

    override fun destroy() {
        recognizer?.destroy()
    }
}