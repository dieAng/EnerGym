package com.dieang.energym.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.dieang.energym.ui.feature.auth.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthGuard(
    content: @Composable () -> Unit,
    onNotLoggedIn: () -> Unit
) {
    val vm: AuthViewModel = hiltViewModel()
    val state = vm.state.collectAsState().value

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn == false) {
            onNotLoggedIn()
        }
    }

    when (state.isLoggedIn) {
        true -> content()
        null -> { /* Cargando estado de sesión */ }
        false -> { /* Redirigiendo... */ }
    }
}
