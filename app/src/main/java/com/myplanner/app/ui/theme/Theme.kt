package com.myplanner.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ─── Futuristic Color Palette ────────────────────────────────────────────────
val NeonBlue       = Color(0xFF00C8FF)
val NeonPurple     = Color(0xFF7C4DFF)
val NeonCyan       = Color(0xFF18FFFF)
val DeepSpace      = Color(0xFF0A0E1A)
val SpaceNavy      = Color(0xFF0D1B2A)
val CardDark       = Color(0xFF111827)
val SurfaceDark    = Color(0xFF161E2E)
val GlassWhite     = Color(0x1AFFFFFF)

// Neumorphism Colors
val NeuDarkShadow  = Color(0xFF05070D)
val NeuLightShadow = Color(0xFF151C2B)
val NeuBackground  = Color(0xFF0D1117)

val PriorityHigh   = Color(0xFFFF4757)
val PriorityMed    = Color(0xFFFFBE00)
val PriorityLow    = Color(0xFF2ED573)

val SubjectColors = listOf(
    Color(0xFF00C8FF), Color(0xFF7C4DFF), Color(0xFFFF6B9D),
    Color(0xFF2ED573), Color(0xFFFFBE00), Color(0xFFFF4757),
    Color(0xFF18FFFF), Color(0xFFFF6348), Color(0xFF5352ED)
)

private val DarkColorScheme = darkColorScheme(
    primary           = NeonBlue,
    onPrimary         = Color(0xFF001F2A),
    primaryContainer  = Color(0xFF003547),
    onPrimaryContainer = NeonCyan,
    secondary         = NeonPurple,
    onSecondary       = Color(0xFF1A0060),
    secondaryContainer = Color(0xFF2D0096),
    onSecondaryContainer = Color(0xFFE9DDFF),
    tertiary          = Color(0xFFFF6B9D),
    background        = DeepSpace,
    onBackground      = Color(0xFFE2E8F0),
    surface           = SurfaceDark,
    onSurface         = Color(0xFFE2E8F0),
    surfaceVariant    = CardDark,
    onSurfaceVariant  = Color(0xFF94A3B8),
    outline           = Color(0xFF334155),
    error             = Color(0xFFFF4757),
)

@Composable
fun MyPlannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = MyPlannerTypography,
        shapes = MyPlannerShapes,
        content = content
    )
}
