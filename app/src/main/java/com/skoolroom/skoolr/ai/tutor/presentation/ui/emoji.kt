package com.skoolroom.skoolr.ai.tutor.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TalkingEmojiFace(isTalking: Boolean) {

    var mouthFrame by remember { mutableStateOf(0) }
    val mouthSequence = listOf(0, 1, 2, 1)

    LaunchedEffect(isTalking) {
        if (isTalking) {
            while (true) {
                delay(150)
                mouthFrame = (mouthFrame + 1) % mouthSequence.size
            }
        } else {
            mouthFrame = 0
        }
    }

    Canvas(modifier = Modifier
        .size(200.dp)
        .padding(16.dp)) {

        val canvasWidth = size.width
        val canvasHeight = size.height

        // Face outline and fill
        drawOval(
            color = Color(0xFFFFA500),
            topLeft = Offset(
                x = (canvasWidth - size.width * 0.9f) / 2f,
                y = (canvasHeight - size.height * 0.8f) / 2f
            ),
            size = Size(
                width = size.width * 0.9f,
                height = size.height * 0.8f
            ),
            style = Stroke(width = 10f)
        )

        drawOval(
            color = Color(0xFFF1C02E),
            topLeft = Offset(
                x = (canvasWidth - size.width * 0.9f) / 2f,
                y = (canvasHeight - size.height * 0.8f) / 2f
            ),
            size = Size(
                width = size.width * 0.9f,
                height = size.height * 0.8f
            )
        )

        // Eyes
        val eyeWidth = size.minDimension / 9
        val eyeHeight = size.minDimension / 7
        val eyeOffsetX = size.width / 6.5f
        val eyeOffsetY = size.height / 2.2f

        drawOval(
            color = Color.Black,
            topLeft = Offset(
                x = canvasWidth / 2f - eyeOffsetX - eyeWidth / 2,
                y = eyeOffsetY - eyeHeight / 2
            ),
            size = Size(eyeWidth, eyeHeight)
        )

        drawOval(
            color = Color.Black,
            topLeft = Offset(
                x = canvasWidth / 2f + eyeOffsetX - eyeWidth / 2,
                y = eyeOffsetY - eyeHeight / 2
            ),
            size = Size(eyeWidth, eyeHeight)
        )

        val reflectionRadius = eyeWidth / 5

        drawCircle(
            color = Color.White,
            radius = reflectionRadius,
            center = Offset(
                x = canvasWidth / 2f - eyeOffsetX - eyeWidth / 4,
                y = eyeOffsetY - eyeHeight / 4
            )
        )

        drawCircle(
            color = Color.White,
            radius = reflectionRadius,
            center = Offset(
                x = canvasWidth / 2f + eyeOffsetX - eyeWidth / 4,
                y = eyeOffsetY - eyeHeight / 4
            )
        )

        val frame = if (!isTalking) 3 else mouthSequence[mouthFrame]

        val mouthWidth = when (frame) {
            0 -> size.width / 3f
            1 -> size.width / 2.7f
            2 -> size.width / 2.5f
            3 -> size.width / 2.2f
            else -> size.width / 2.7f
        }

        val mouthHeight = when (frame) {
            0 -> size.height / 2.5f
            1 -> size.height / 3.2f
            2 -> size.height / 3.5f
            3 -> size.height / 3.5f
            else -> size.height / 3.2f
        }

        val mouthTop = when (frame) {
            0 -> canvasHeight * 0.82f
            1 -> canvasHeight * 0.80f
            2 -> canvasHeight * 0.78f
            3 -> canvasHeight * 0.76f
            else -> canvasHeight * 0.80f
        }

        val mouthLeft = (canvasWidth - mouthWidth) / 2f

        if(isTalking){
            val mouthPath = Path().apply {
                moveTo(mouthLeft, mouthTop - mouthHeight / 2)

                arcTo(
                    rect = Rect(
                        offset = Offset(mouthLeft, mouthTop - mouthHeight),
                        size = Size(mouthWidth, mouthHeight)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )

                lineTo(mouthLeft + mouthWidth, mouthTop - mouthHeight / 2)

                close()
            }

            drawPath(
                path = mouthPath,
                color = Color.Black
            )
        } else {
            val staticMouthWidth = size.width / 3f
            val staticMouthHeight = size.height / 6f
            val staticMouthTop = canvasHeight * 0.55f
            val staticMouthLeft = (canvasWidth - staticMouthWidth) / 2f

            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(
                    staticMouthLeft,
                    staticMouthTop
                ),
                size = Size(staticMouthWidth, staticMouthHeight),
                style = Stroke(width = 15f)
            )
        }
    }
}
