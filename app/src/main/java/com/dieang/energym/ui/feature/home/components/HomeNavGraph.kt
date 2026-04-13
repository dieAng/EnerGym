package com.dieang.energym.ui.feature.home.components

import androidx.compose.runtime.collectAsState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.home.HomeScreen
import com.dieang.energym.ui.feature.home.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(Routes.HOME) {
        val vm: HomeViewModel = hiltViewModel()
        HomeScreen(
            state = vm.state.collectAsState().value,
            onEvent = vm::onEvent,
            onNavigateRecetas = { navController.navigate(Routes.RECETAS) },
            onNavigateRutinas = { navController.navigate(Routes.RUTINAS) },
            onNavigateSesiones = { navController.navigate(Routes.SESIONES) }
        )
    }
}

