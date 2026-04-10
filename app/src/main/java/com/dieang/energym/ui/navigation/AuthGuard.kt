package com.dieang.energym.ui.navigation

import androidx.compose.runtime.Composable

@Composable
fun AuthGuard(
    authViewModel: AuthViewModel,
    onAuthenticated: @Composable () -> Unit,
    onUnauthenticated: @Composable () -> Unit
) {
    val state by authViewModel.state.collectAsState()

    when {
        state.isLoggedIn -> onAuthenticated()
        else -> onUnauthenticated()
    }
}
