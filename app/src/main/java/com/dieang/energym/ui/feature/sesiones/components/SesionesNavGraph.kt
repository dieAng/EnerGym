package com.dieang.energym.ui.feature.sesiones.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.sesiones.SesionViewModel
import com.dieang.energym.ui.feature.sesiones.SesionesScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.sesionesNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.SESIONES,
        route = Routes.SESIONES
    ) {
        composable(Routes.SESIONES) {
            val vm: SesionViewModel = hiltViewModel()
            SesionesScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToDetail = { id -> navController.navigate("${Routes.SESION_DETAIL}/$id") }
            )
        }

        composable("${Routes.SESION_DETAIL}/{id}") {
            val vm: SesionViewModel = hiltViewModel()
            SesionDetailScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
    }
}