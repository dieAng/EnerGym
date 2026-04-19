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
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}