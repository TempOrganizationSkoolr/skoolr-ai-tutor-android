package com.skoolroom.skoolr.ai.tutor.data.ai.gemini

import com.skoolroom.skoolr.ai.tutor.BuildConfig
import com.skoolroom.skoolr.ai.tutor.domain.model.gemini.GeminiContent
import com.skoolroom.skoolr.ai.tutor.domain.model.gemini.GeminiPart
import com.skoolroom.skoolr.ai.tutor.domain.model.gemini.GeminiRequest
import com.skoolroom.skoolr.ai.tutor.domain.model.gemini.GeminiResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

suspend fun getGeminiResponse(prompt: String): String {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val request = GeminiRequest(
        contents = listOf(
            GeminiContent(
                parts = listOf(
                    GeminiPart(text = prompt)
                )
            )
        )
    )

    val response: GeminiResponse = client.post(
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"
    ) {
        contentType(ContentType.Application.Json)
        header("X-goog-api-key", BuildConfig.GEMINI_API_KEY)
        setBody(request)
    }.body()

    return response.candidates
        .firstOrNull()
        ?.content
        ?.parts
        ?.firstOrNull()
        ?.text ?: "No response."
}