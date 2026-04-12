package com.dieang.energym.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dieang.energym.ui.feature.auth.AuthViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    composable(Routes.LOGIN) {
        val vm: AuthViewModel = hiltViewModel()

        LoginScreen(
            state = vm.state.collectAsState().value,
            onLogin = { email, pass -> vm.login(email, pass) },
            onSuccess = { navController.navigate(Routes.HOME) }
        )
    }
}

