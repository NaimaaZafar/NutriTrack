package com.nutritrack.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF4C90AF),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF3B388E),
    secondary = androidx.compose.ui.graphics.Color(0xFF2196F3),
    secondaryVariant = androidx.compose.ui.graphics.Color(0xFF1976D2),
    background = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color.White,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onBackground = androidx.compose.ui.graphics.Color.Black,
    onSurface = androidx.compose.ui.graphics.Color.Black
)

@Composable
fun NutriTrackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        content = content
    )
} 