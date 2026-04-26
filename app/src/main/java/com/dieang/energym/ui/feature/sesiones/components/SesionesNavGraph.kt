package com.dieang.energym.ui.feature.sesiones.components

import androidx.compose.runtime.Composable
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

@Composable
fun SesionesNavGraph(navController: NavController) {
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
        SesionActiveScreen(
            state = state,
            onToggleSerie = { num -> vm.toggleSerie(num) },
            onFinish = { vm.finishSesion() },
            onClose = { navController.popBackStack() }
        )
    }
}
