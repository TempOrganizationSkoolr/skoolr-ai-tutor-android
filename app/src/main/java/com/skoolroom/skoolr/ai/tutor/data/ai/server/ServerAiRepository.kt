package com.skoolroom.skoolr.ai.tutor.data.ai.server

import com.skoolroom.skoolr.ai.tutor.domain.ai.AiRepository

class ServerAiRepository: AiRepository {

    override suspend fun getResponse(prompt: String): String {
        return "AI Response for prompt: $prompt"
    }
}