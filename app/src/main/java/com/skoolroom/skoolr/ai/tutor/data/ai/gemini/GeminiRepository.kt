package com.skoolroom.skoolr.ai.tutor.data.ai.gemini

import com.skoolroom.skoolr.ai.tutor.domain.ai.AiRepository

class GeminiRepository : AiRepository {
    override suspend fun getResponse(prompt: String): String {
        return getGeminiResponse(prompt)
    }
}