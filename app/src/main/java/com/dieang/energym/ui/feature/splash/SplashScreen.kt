package com.dieang.energym.ui.feature.splash

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun SplashScreen(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) { onEvent(SplashEvent.CheckSession) }

    LaunchedEffect(state.isLoggedIn) {
        when (state.isLoggedIn) {
            true -> onNavigateToHome()
            false -> onNavigateToLogin()
            null -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    EnerGymTheme {
        SplashScreen(
            state = SplashState(),
            onEvent = {},
            onNavigateToLogin = {},
            onNavigateToHome = {}
        )
    }
}
