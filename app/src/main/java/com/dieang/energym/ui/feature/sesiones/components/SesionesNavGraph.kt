package com.dieang.energym.ui.feature.sesiones.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.sesiones.*
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.sesionesNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.SESIONES,
        route = "sesiones_graph"
    ) {
        composable(Routes.SESIONES) {
            val vm: SesionViewModel = hiltViewModel()
            val state = vm.state.collectAsState().value

            if (state.isFinished && state.resumenFinal != null) {
                SesionSummaryScreen(
                    resumen = state.resumenFinal,
                    onDismiss = {
                        // Navegar a casa y limpiar stack de sesión
                        navController.navigate(Routes.HOME) {
                            popUpTo("sesiones_graph") { inclusive = true }
                        }
                    }
                )
            } else {
                SesionActiveScreen(
                    state = state,
                    onToggleSerie = { num -> vm.toggleSerie(num) },
                    onFinish = { vm.finishSesion() },
                    onClose = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = Routes.SESION_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            // Esta ruta se puede usar para el historial o redirigir a la activa
            val vm: SesionViewModel = hiltViewModel()
            val state = vm.state.collectAsState().value
            
            SesionActiveScreen(
                state = state,
                onToggleSerie = { num -> vm.toggleSerie(num) },
                onFinish = { vm.finishSesion() },
                onClose = { navController.popBackStack() }
            )
        }
    }
}
