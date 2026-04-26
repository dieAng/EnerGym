package com.dieang.energym.ui.feature.splash.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.splash.SplashScreen
import com.dieang.energym.ui.feature.splash.SplashViewModel
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.splashNavGraph(navController: NavController) {
    composable(Routes.SPLASH) {
        val vm: SplashViewModel = hiltViewModel()
        val state = vm.state.collectAsState().value

        SplashScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateToLogin = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            },
            onNavigateToHome = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        )
    }
}
