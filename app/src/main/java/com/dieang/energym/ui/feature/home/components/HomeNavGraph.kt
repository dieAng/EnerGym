package com.dieang.energym.ui.feature.home.components

import androidx.compose.runtime.collectAsState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.home.HomeScreen
import com.dieang.energym.ui.feature.home.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable("home") {
        val vm: HomeViewModel = hiltViewModel()
        val state = vm.state.collectAsState().value

        HomeScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateRecetas = { navController.navigate("recetas") },
            onNavigateRutinas = { navController.navigate("rutinas") },
            onNavigateSesiones = { navController.navigate("sesiones") }
        )
    }
}
