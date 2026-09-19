package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = UnipointSkyLight,
    onPrimary = Slate900,
    primaryContainer = UnipointNavyLight,
    onPrimaryContainer = Color.White,
    secondary = UnipointAmberLight,
    onSecondary = Slate900,
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = Color(0xFFFEF3C7),
    tertiary = UnipointAmber,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Slate100,
    onSurface = Slate100,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Slate200,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = UnipointNavy,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = UnipointNavyDark,
    secondary = UnipointAmber,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF78350F),
    tertiary = UnipointSky,
    background = Slate50,
    surface = Color.White,
    onBackground = Slate900,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate700,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
