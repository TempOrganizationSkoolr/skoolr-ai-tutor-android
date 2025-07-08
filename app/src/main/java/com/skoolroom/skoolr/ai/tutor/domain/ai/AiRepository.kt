package com.skoolroom.skoolr.ai.tutor.domain.ai

interface AiRepository {
    suspend fun getResponse(prompt: String): String
}