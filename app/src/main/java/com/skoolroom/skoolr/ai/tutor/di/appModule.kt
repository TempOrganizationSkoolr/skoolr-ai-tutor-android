package com.skoolroom.skoolr.ai.tutor.di

import com.skoolroom.skoolr.ai.tutor.presentation.AiTutorViewModel
import com.skoolroom.skoolr.ai.tutor.domain.ai.AiRepository
import com.skoolroom.skoolr.ai.tutor.data.ai.gemini.GeminiRepository
import com.skoolroom.skoolr.ai.tutor.data.speech.SpeechRecognizerManager
import com.skoolroom.skoolr.ai.tutor.data.tts.TextToSpeechManager
import com.skoolroom.skoolr.ai.tutor.domain.speech.SpeechRecognizerManagerInterface
import com.skoolroom.skoolr.ai.tutor.domain.tts.TextToSpeechManagerInterface
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AiRepository> { GeminiRepository() }
    single<TextToSpeechManagerInterface> { TextToSpeechManager(get()) }
    single<SpeechRecognizerManagerInterface> { SpeechRecognizerManager(get()) }
    viewModel { AiTutorViewModel(get(), get(), get()) }
}
