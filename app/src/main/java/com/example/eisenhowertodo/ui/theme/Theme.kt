package com.example.eisenhowertodo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MinimalColorScheme = lightColorScheme(
    primary = Black,
    onPrimary = White,
    secondary = DarkGray,
    onSecondary = White,
    tertiary = MediumGray,
    onTertiary = Black,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    surfaceVariant = DarkGray,
    onSurfaceVariant = White,
    outline = MediumGray,
    outlineVariant = DarkGray
)

@Composable
fun EisenhowerToDoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MinimalColorScheme,
        typography = Typography,
        content = content
    )
}