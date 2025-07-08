package com.skoolroom.skoolr.ai.tutor.domain.model

sealed class RecognitionResult {
    data class State(val message: String) : RecognitionResult()
    data class Result(val text: String) : RecognitionResult()
    data class Error(val error: String) : RecognitionResult()
}
