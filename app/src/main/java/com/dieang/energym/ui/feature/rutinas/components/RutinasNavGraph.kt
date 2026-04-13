package com.dieang.energym.ui.feature.rutinas.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.rutinas.RutinaViewModel
import com.dieang.energym.ui.feature.rutinas.RutinasScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.rutinasNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.RUTINAS,
        route = Routes.RUTINAS
    ) {
        composable(Routes.RUTINAS) {
            val vm: RutinaViewModel = hiltViewModel()
            RutinasScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToDetail = { id -> navController.navigate("${Routes.RUTINA_DETAIL}/$id") }
            )
        }

        composable("${Routes.RUTINA_DETAIL}/{id}") {
            val vm: RutinaViewModel = hiltViewModel()
            RutinaDetailScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
    }
}