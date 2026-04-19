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
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.rutinasNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.RUTINAS,
        route = "rutinas_graph"
    ) {
        composable(Routes.RUTINAS) {
            val vm: RutinaViewModel = hiltViewModel()
            RutinasScreen(
                state = vm.state.collectAsState().value,
                onSelect = { id ->
                    navController.navigate(Routes.RUTINA_DETAIL.replace("{id}", id.toString()))
                }
            )
        }

        composable(
            route = Routes.RUTINA_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")
            val rutinaId = idString?.let { UUID.fromString(it) } ?: UUID.randomUUID()
            val vm: RutinaViewModel = hiltViewModel()

            RutinaDetailScreen(
                rutinaId = rutinaId,
                state = vm.state.collectAsState().value,
                onLoad = { id -> vm.loadRutinaDetalle(id) },
                onStartWorkout = { id ->
                    navController.navigate(Routes.SESION_DETAIL.replace("{id}", id.toString()))
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
