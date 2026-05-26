package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class RobotState {
    IDLE,
    THINKING,
    TALKING
}

@Composable
fun RobotAssistant(
    modifier: Modifier = Modifier,
    state: RobotState = RobotState.IDLE,
    onClick: () -> Unit = {}
) {
    // Floating continuous hover animation (3D up and down displacement)
    val infiniteTransition = rememberInfiniteTransition(label = "robot_hover")
    val hoverY by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_hover_y"
    )

    // Breathing scale animation
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_breathing"
    )

    // Eye blinking timer
    var eyeBlinkScale by remember { mutableStateOf(1f) }
    LaunchedEffect(key1 = true) {
        while (true) {
            delay((3000..6000).random().toLong())
            // Shut eyes
            eyeBlinkScale = 0.1f
            delay(120)
            // Open eyes
            eyeBlinkScale = 1f
            // Double blink potential
            if ((1..10).random() > 6) {
                delay(80)
                eyeBlinkScale = 0.1f
                delay(120)
                eyeBlinkScale = 1f
            }
        }
    }

    // Thinking glowing animation modifier
    val activeIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_thinking_glow"
    )

    // Mouth talking wave animation
    val talkingWave1 by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = EaseInOutBounce),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_talk_1"
    )

    val talkingWave2 by infiniteTransition.animateFloat(
        initialValue = 16f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(350, easing = EaseInOutBounce),
            repeatMode = RepeatMode.Reverse
        ),
        label = "robot_talk_2"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(y = hoverY.dp)
                .graphicsLayer(
                    scaleX = breathingScale,
                    scaleY = breathingScale
                ),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Robot Colors (Futuristic Teal and Ice Blue Metallic gradients)
                val bodyBrush = Brush.radialGradient(
                    colors = listOf(Color(0xFFE0FFFF), Color(0xFF6A5ACD), Color(0xFF191970)),
                    center = Offset(canvasWidth * 0.4f, canvasHeight * 0.4f),
                    radius = canvasWidth * 0.5f
                )

                val facePlateBrush = Brush.radialGradient(
                    colors = listOf(Color(0xFF2B2B2B), Color(0xFF0F0F0F)),
                    center = Offset(canvasWidth / 2f, canvasHeight / 2f),
                    radius = canvasWidth * 0.35f
                )

                val metallicOutlineBrush = Brush.linearGradient(
                    colors = listOf(Color.White.copy(alpha = 0.6f), Color.Transparent, Color(0xFF1E90FF).copy(alpha = 0.5f))
                )

                // 1. Draw Antenna
                val antBaseX = canvasWidth / 2f
                val antBaseY = canvasHeight * 0.2f
                drawLine(
                    brush = Brush.linearGradient(listOf(Color.White, Color(0xFF00FFFF))),
                    start = Offset(antBaseX, antBaseY),
                    end = Offset(antBaseX, canvasHeight * 0.05f),
                    strokeWidth = 6f
                )

                // Antenna Tip Glow
                val antGlowColor = when (state) {
                    RobotState.IDLE -> Color(0xFF00FFCC)
                    RobotState.THINKING -> Color(0xFFFFCC00)
                    RobotState.TALKING -> Color(0xFF00FFFF)
                }
                drawCircle(
                    color = antGlowColor,
                    center = Offset(antBaseX, canvasHeight * 0.03f),
                    radius = if (state == RobotState.THINKING) 12f * (1f + activeIntensity * 0.4f) else 10f
                )
                // Outer glow
                drawCircle(
                    color = antGlowColor.copy(alpha = 0.4f),
                    center = Offset(antBaseX, canvasHeight * 0.03f),
                    radius = if (state == RobotState.THINKING) 22f * activeIntensity else 16f
                )

                // Antenna Ears
                drawCircle(
                    color = Color(0xFF4682B4),
                    center = Offset(canvasWidth * 0.2f, canvasHeight * 0.4f),
                    radius = 12f
                )
                drawCircle(
                    color = Color(0xFF4682B4),
                    center = Offset(canvasWidth * 0.8f, canvasHeight * 0.4f),
                    radius = 12f
                )

                // 2. Draw Main Base Head (Shiny Metallic rounded square/sphere)
                drawRoundRect(
                    brush = bodyBrush,
                    topLeft = Offset(canvasWidth * 0.2f, canvasHeight * 0.22f),
                    size = Size(canvasWidth * 0.6f, canvasHeight * 0.48f),
                    cornerRadius = CornerRadius(48f, 48f)
                )

                // Shiny metallic highlight rim
                drawRoundRect(
                    brush = metallicOutlineBrush,
                    topLeft = Offset(canvasWidth * 0.2f, canvasHeight * 0.22f),
                    size = Size(canvasWidth * 0.6f, canvasHeight * 0.48f),
                    cornerRadius = CornerRadius(48f, 48f),
                    style = Stroke(width = 4f)
                )

                // 3. Inner Dark Glass Display Face
                drawRoundRect(
                    brush = facePlateBrush,
                    topLeft = Offset(canvasWidth * 0.25f, canvasHeight * 0.28f),
                    size = Size(canvasWidth * 0.5f, canvasHeight * 0.36f),
                    cornerRadius = CornerRadius(32f, 32f)
                )

                // Face bezel highlight
                drawRoundRect(
                    color = Color(0xFF1E90FF).copy(alpha = 0.3f),
                    topLeft = Offset(canvasWidth * 0.25f, canvasHeight * 0.28f),
                    size = Size(canvasWidth * 0.5f, canvasHeight * 0.36f),
                    cornerRadius = CornerRadius(32f, 32f),
                    style = Stroke(width = 2f)
                )

                // 4. Glowing Cyber Eyes (Blinking + LED tracking)
                val eyeY = canvasHeight * 0.4f
                val leftEyeX = canvasWidth * 0.38f
                val rightEyeX = canvasWidth * 0.62f
                val eyeColor = when (state) {
                    RobotState.IDLE -> Color(0xFF00FFCC)
                    RobotState.THINKING -> Color(0xFFFFCC00)
                    RobotState.TALKING -> Color(0xFF00FFFF)
                }

                // Left Eye (Outer glow + Core)
                drawCircle(
                    color = eyeColor.copy(alpha = 0.3f),
                    center = Offset(leftEyeX, eyeY),
                    radius = 24f
                )
                drawCircle(
                    color = eyeColor,
                    center = Offset(leftEyeX, eyeY),
                    radius = 16f,
                )
                // Left Eye iris blink shape
                if (eyeBlinkScale < 1f || state == RobotState.THINKING) {
                    // Draw blinking eyelid (horizontal mask)
                    drawRect(
                        color = Color(0xFF0F0F0F),
                        topLeft = Offset(leftEyeX - 25f, eyeY - 20f),
                        size = Size(50f, 40f * (1f - eyeBlinkScale))
                    )
                } else {
                    // Puppy/Cyborg eye specular highlight
                    drawCircle(
                        color = Color.White,
                        center = Offset(leftEyeX - 5f, eyeY - 5f),
                        radius = 4f
                    )
                }

                // Right Eye (Outer glow + Core)
                drawCircle(
                    color = eyeColor.copy(alpha = 0.3f),
                    center = Offset(rightEyeX, eyeY),
                    radius = 24f
                )
                drawCircle(
                    color = eyeColor,
                    center = Offset(rightEyeX, eyeY),
                    radius = 16f,
                )
                // Right Eye iris blink shape
                if (eyeBlinkScale < 1f || state == RobotState.THINKING) {
                    drawRect(
                        color = Color(0xFF0F0F0F),
                        topLeft = Offset(rightEyeX - 25f, eyeY - 20f),
                        size = Size(50f, 40f * (1f - eyeBlinkScale))
                    )
                } else {
                    drawCircle(
                        color = Color.White,
                        center = Offset(rightEyeX - 5f, eyeY - 5f),
                        radius = 4f
                    )
                }

                // 5. Robo Mouth (Reactive LED display)
                val mouthY = canvasHeight * 0.52f
                val mouthWidth = canvasWidth * 0.25f
                val mouthX = (canvasWidth - mouthWidth) / 2f

                when (state) {
                    RobotState.IDLE -> {
                        // Smiley happy electronic arc curve mouth
                        val p = Path().apply {
                            moveTo(mouthX, mouthY)
                            quadraticBezierTo(
                                mouthX + mouthWidth / 2f, mouthY + 12f,
                                mouthX + mouthWidth, mouthY
                            )
                        }
                        drawPath(
                            path = p,
                            color = Color(0xFF00FFCC),
                            style = Stroke(width = 4f, cap = StrokeCap.Round)
                        )
                    }
                    RobotState.THINKING -> {
                        // Oscillating horizontal led dot array (Left & Right loader wave)
                        val pointsCount = 6
                        val dotColors = Color(0xFFFFCC00)
                        for (i in 0 until pointsCount) {
                            val dotX = mouthX + (mouthWidth / (pointsCount - 1)) * i
                            val dotHeightOffset = Math.sin((activeIntensity * Math.PI * 2) + i).toFloat() * 8f
                            drawCircle(
                                color = dotColors,
                                center = Offset(dotX, mouthY + dotHeightOffset),
                                radius = 4f
                            )
                        }
                    }
                    RobotState.TALKING -> {
                        // Sound amplitude speech bar meters fluctuating
                        val barCount = 5
                        val barMaxHeight = 24f
                        for (i in 0 until barCount) {
                            val barX = mouthX + (mouthWidth / (barCount - 1)) * i
                            val amplitudeHeight = if (i % 2 == 0) talkingWave1 else talkingWave2
                            drawLine(
                                color = Color(0xFF00FFFF),
                                start = Offset(barX, mouthY - amplitudeHeight / 2f),
                                end = Offset(barX, mouthY + amplitudeHeight / 2f),
                                strokeWidth = 5f,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }

                // Base Support neck collar
                drawRoundRect(
                    color = Color(0xFF708090),
                    topLeft = Offset(canvasWidth * 0.42f, canvasHeight * 0.7f),
                    size = Size(canvasWidth * 0.16f, canvasHeight * 0.08f),
                    cornerRadius = CornerRadius(10f, 10f)
                )

                // 3D Chest/Body portion representation
                val chestPath = Path().apply {
                    moveTo(canvasWidth * 0.35f, canvasHeight * 0.78f)
                    lineTo(canvasWidth * 0.65f, canvasHeight * 0.78f)
                    lineTo(canvasWidth * 0.72f, canvasHeight * 0.95f)
                    lineTo(canvasWidth * 0.28f, canvasHeight * 0.95f)
                    close()
                }

                drawPath(
                    path = chestPath,
                    brush = Brush.verticalGradient(listOf(Color(0xFF4B0082), Color(0xFF000000)))
                )

                // Glowing chest LED energy core
                drawCircle(
                    color = when (state) {
                        RobotState.IDLE -> Color(0xFF00FFCC).copy(alpha = 0.2f + activeIntensity * 0.3f)
                        RobotState.THINKING -> Color(0xFFFFCC00).copy(alpha = 0.2f + activeIntensity * 0.5f)
                        RobotState.TALKING -> Color(0xFF00FFFF).copy(alpha = 0.2f + activeIntensity * 0.6f)
                    },
                    center = Offset(canvasWidth / 2f, canvasHeight * 0.86f),
                    radius = 16f
                )
                drawCircle(
                    color = when (state) {
                        RobotState.IDLE -> Color(0xFF00FFCC)
                        RobotState.THINKING -> Color(0xFFFFCC00)
                        RobotState.TALKING -> Color(0xFF00FFFF)
                    },
                    center = Offset(canvasWidth / 2f, canvasHeight * 0.86f),
                    radius = 8f
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Simple overlay description or speech balloon of robot's activity
        Text(
            text = when (state) {
                RobotState.IDLE -> "মিত্র রোবট (অনলাইন)"
                RobotState.THINKING -> "এআই চিন্তা করছে..."
                RobotState.TALKING -> "রোবট উত্তর দিচ্ছে..."
            },
            color = when (state) {
                RobotState.IDLE -> Color(0xFF00FFCC)
                RobotState.THINKING -> Color(0xFFFFCC00)
                RobotState.TALKING -> Color(0xFF00FFFF)
            },
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
