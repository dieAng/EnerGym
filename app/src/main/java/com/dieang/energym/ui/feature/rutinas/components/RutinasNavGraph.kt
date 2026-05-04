package com.dieang.energym.ui.feature.rutinas.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.rutinas.RutinaDetailScreen
import com.dieang.energym.ui.feature.rutinas.RutinaViewModel
import com.dieang.energym.ui.feature.rutinas.RutinasScreen
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.rutinasNavGraph(navController: NavController) {
    navigation(
        startDestination = "rutinas_main",
        route = Routes.RUTINAS
    ) {
        composable("rutinas_main") {
            AuthGuard(
                content = {
                    val vm: RutinaViewModel = hiltViewModel()
                    RutinasScreen(
                        state = vm.state.collectAsState().value,
                        onNavigateDetail = { id ->
                            navController.navigate(Routes.RUTINA_DETAIL.replace("{id}", id.toString()))
                        }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(
            route = Routes.RUTINA_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = UUID.fromString(backStackEntry.arguments?.getString("id") ?: "")
            AuthGuard(
                content = {
                    val vm: RutinaViewModel = hiltViewModel()
                    RutinaDetailScreen(
                        rutinaId = id,
                        state = vm.state.collectAsState().value,
                        onLoad = vm::loadRutinaDetalle,
                        onStartWorkout = { 
                            // Navegar a la pantalla de sesión activa con esta rutina
                            navController.navigate(Routes.SESION_ACTIVA.replace("{rutinaId}", id.toString()))
                        },
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
