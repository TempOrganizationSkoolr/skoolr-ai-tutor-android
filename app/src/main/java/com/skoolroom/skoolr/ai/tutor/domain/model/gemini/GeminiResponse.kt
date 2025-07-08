package com.skoolroom.skoolr.ai.tutor.domain.model.gemini

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<GeminiCandidate> = emptyList()
)

@Serializable
data class GeminiCandidate(
    val content: GeminiContent
)