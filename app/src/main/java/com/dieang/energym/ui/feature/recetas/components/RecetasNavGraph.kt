package com.dieang.energym.ui.feature.recetas.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.recetas.RecetaViewModel
import com.dieang.energym.ui.feature.recetas.RecetasScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.recetasNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.RECETAS,
        route = Routes.RECETAS
    ) {
        composable(Routes.RECETAS) {
            val vm: RecetaViewModel = hiltViewModel()
            RecetasScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToDetail = { id -> navController.navigate("${Routes.RECETA_DETAIL}/$id") }
            )
        }

        composable("${Routes.RECETA_DETAIL}/{id}") {
            val vm: RecetaViewModel = hiltViewModel()
            RecetaDetailScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
