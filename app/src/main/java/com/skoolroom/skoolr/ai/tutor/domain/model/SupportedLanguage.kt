package com.skoolroom.skoolr.ai.tutor.domain.model

import java.util.Locale

enum class SupportedLanguage(
    val recognitionLocaleCode: String,
    val ttsLocale: Locale
) {
    ENGLISH("en-US", Locale.US),
    FRENCH("fr-FR", Locale.FRENCH),
    GERMAN("de-DE", Locale.GERMAN),
    SPANISH("es-ES", Locale("es", "ES"))
}