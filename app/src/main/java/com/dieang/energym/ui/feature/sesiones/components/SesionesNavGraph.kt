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
import com.dieang.energym.ui.feature.usuario.SesionDetailViewModel
import com.dieang.energym.ui.feature.usuario.SesionDetailScreen as HistoryDetailScreen
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.sesionesNavGraph(navController: NavController) {
    navigation(
        startDestination = "sesiones_history",
        route = Routes.SESIONES
    ) {
        // 1. Historial de Sesiones
        composable("sesiones_history") {
            AuthGuard(
                content = {
                    val vm: SesionViewModel = hiltViewModel()
                    SesionesScreen(
                        state = vm.state.collectAsState().value,
                        onRefresh = { },
                        onSelect = { id ->
                            navController.navigate(Routes.SESION_DETAIL.replace("{id}", id.toString()))
                        },
                        onFilterMes = { },
                        onFilterTipo = { }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // 2. Detalle de Sesión Pasada (History)
        composable(
            route = Routes.SESION_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            AuthGuard(
                content = {
                    val vm: SesionDetailViewModel = hiltViewModel()
                    HistoryDetailScreen(
                        state = vm.state.collectAsState().value,
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // 3. Sesión Activa (Entrenamiento en curso)
        composable(
            route = Routes.SESION_ACTIVA,
            arguments = listOf(navArgument("rutinaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rutinaIdStr = backStackEntry.arguments?.getString("rutinaId") ?: ""
            AuthGuard(
                content = {
                    val vm: SesionViewModel = hiltViewModel()
                    val state = vm.state.collectAsState().value

                    if (state.isFinished && state.resumenFinal != null) {
                        SesionSummaryScreen(
                            resumen = state.resumenFinal,
                            onShare = { },
                            onClose = {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.SESIONES) { inclusive = true }
                                }
                            }
                        )
                    } else {
                        // Aquí usamos el SesionDetailScreen del paquete 'sesiones' 
                        // que es el que maneja la sesión activa
                        SesionDetailScreen(
                            rutinaId = UUID.fromString(rutinaIdStr),
                            state = state,
                            onStart = { rid -> vm.startSesion(rid) },
                            onRegisterSerie = { eid, reps, peso -> vm.registrarSerie(eid, reps, peso) },
                            onFinish = { vm.finishSesion() },
                            onBack = { navController.popBackStack() }
                        )
                    }
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
