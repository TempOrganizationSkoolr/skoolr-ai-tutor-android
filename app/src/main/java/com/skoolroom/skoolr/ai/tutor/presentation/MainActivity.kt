package com.skoolroom.skoolr.ai.tutor.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.skoolroom.skoolr.ai.tutor.presentation.ui.SpeechRecognitionScreen
import com.skoolroom.skoolr.ai.tutor.presentation.ui.theme.SkoolrAiTutorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkoolrAiTutorTheme {
                SpeechRecognitionScreen()
            }
        }
    }
}