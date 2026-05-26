package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Frosted Glass Palette
val Slate950 = Color(0xFF020617)
val Slate900 = Color(0xFF0F172A)
val Slate800 = Color(0xFF1E293B)
val Slate200 = Color(0xFFE2E8F0)
val Slate400 = Color(0xFF94A3B8)

val ColorIndigo = Color(0xFF6366F1)
val ColorCyan = Color(0xFF22D3EE)

// Translucent Glass Color schemes as defaults
private val FrostedColorScheme = darkColorScheme(
    primary = ColorIndigo,
    onPrimary = Color.White,
    primaryContainer = ColorIndigo.copy(alpha = 0.25f),
    onPrimaryContainer = Color.White,
    secondary = ColorCyan,
    onSecondary = Slate950,
    secondaryContainer = ColorCyan.copy(alpha = 0.25f),
    onSecondaryContainer = ColorCyan,
    tertiary = ColorIndigo,
    background = Slate950,
    onBackground = Slate200,
    surface = Slate900.copy(alpha = 0.6f),
    onSurface = Slate200,
    surfaceVariant = Color.White.copy(alpha = 0.08f),
    onSurfaceVariant = Slate200,
    outline = Color.White.copy(alpha = 0.18f),
    outlineVariant = Color.White.copy(alpha = 0.08f)
)

// Beautiful glowing backdrops
fun Modifier.frostedBackground(): Modifier = this.drawBehind {
    // Fill slate-950 base
    drawRect(Slate950)
    
    // Indigo glow orb in top left
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(ColorIndigo.copy(alpha = 0.20f), Color.Transparent),
            center = Offset(size.width * 0.15f, size.height * 0.25f),
            radius = size.width * 0.85f
        ),
        radius = size.width * 0.85f,
        center = Offset(size.width * 0.15f, size.height * 0.25f)
    )
    
    // Cyan glow orb in bottom right
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(ColorCyan.copy(alpha = 0.20f), Color.Transparent),
            center = Offset(size.width * 0.85f, size.height * 0.75f),
            radius = size.width * 0.85f
        ),
        radius = size.width * 0.85f,
        center = Offset(size.width * 0.85f, size.height * 0.75f)
    )
}

// Global modifier for glass elements with borders
fun Modifier.glassCardModifier(
    shape: Shape,
    borderWidth: Dp = 1.dp
): Modifier = this
    .background(
        color = Color.White.copy(alpha = 0.07f),
        shape = shape
    )
    .border(
        width = borderWidth,
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.22f),
                Color.White.copy(alpha = 0.03f)
            )
        ),
        shape = shape
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // disabled by default to force our premium Frosted Glass theme!
    content: @Composable () -> Unit,
) {
    // Keep consistent immersive dark-mode Frosted Glass aesthetic!
    val colorScheme = FrostedColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
