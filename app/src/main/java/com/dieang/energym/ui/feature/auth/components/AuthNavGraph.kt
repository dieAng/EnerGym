package com.dieang.energym.ui.feature.auth.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.auth.LoginScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.LOGIN,
        route = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            val vm = hiltViewModel<LoginViewModel>()
            LoginScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateHome = { navController.navigate(Routes.HOME) },
                onNavigateRegister = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.REGISTER) {
            val vm = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateLogin = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
