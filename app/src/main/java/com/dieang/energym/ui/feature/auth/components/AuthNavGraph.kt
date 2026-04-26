package com.dieang.energym.ui.feature.auth.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.auth.AuthViewModel
import com.dieang.energym.ui.feature.auth.LoginScreen
import com.dieang.energym.ui.feature.auth.RegisterScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.LOGIN,
        route = "auth_graph"
    ) {
        composable(Routes.LOGIN) {
            val vm: AuthViewModel = hiltViewModel()
            LoginScreen(
                state = vm.state.collectAsState().value,
                onLogin = vm::login,
                onSuccess = { navController.navigate(Routes.HOME) },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.REGISTER) {
            val vm: AuthViewModel = hiltViewModel()
            RegisterScreen(
                state = vm.state.collectAsState().value,
                onRegister = vm::register,
                onNavigateLogin = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
