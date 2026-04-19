package com.dieang.energym.ui.feature.recetas.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.recetas.RecetaDetailScreen
import com.dieang.energym.ui.feature.recetas.RecetaViewModel
import com.dieang.energym.ui.feature.recetas.RecetasScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.recetasNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.RECETAS,
        route = "recetas_graph"
    ) {
        composable(Routes.RECETAS) {
            val vm: RecetaViewModel = hiltViewModel()
            RecetasScreen(
                state = vm.state.collectAsState().value,
                onNavigateDetail = { id ->
                    navController.navigate(Routes.RECETA_DETAIL.replace("{id}", id))
                }
            )
        }

        composable(
            route = Routes.RECETA_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getString("id") ?: ""
            val vm: RecetaViewModel = hiltViewModel()

            RecetaDetailScreen(
                recetaId = recetaId,
                state = vm.state.collectAsState().value,
                onLoad = { id -> vm.loadRecetaDetalle(id) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
