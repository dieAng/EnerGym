package com.dieang.energym.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EnerGymBlue,
    secondary = EnerGymGreen,
    tertiary = EnerGymPurple,
    background = Color(0xFF0F172A), // Fondo oscuro basado en Slate 900
    surface = Color(0xFF1E293B),    // Superficie oscura basado en Slate 800
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = EnerGymBlue,
    secondary = EnerGymGreen,
    tertiary = EnerGymPurple,
    background = EnerGymBackground,
    surface = EnerGymSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = EnerGymTextPrimary,
    onSurface = EnerGymTextPrimary,
    outline = EnerGymDivider
)

@Composable
fun EnerGymTheme(
    darkTheme: Boolean = false, // Forzar Light Theme para coincidir con Figma inicialmente
    dynamicColor: Boolean = false, // Desactivar Dynamic Color para mantener colores de marca
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}