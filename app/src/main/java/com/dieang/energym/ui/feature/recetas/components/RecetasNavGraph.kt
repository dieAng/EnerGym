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
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.recetasNavGraph(navController: NavController) {
    navigation(
        startDestination = "recetas_main",
        route = Routes.RECETAS
    ) {
        composable("recetas_main") {
            AuthGuard(
                content = {
                    val vm: RecetaViewModel = hiltViewModel()
                    RecetasScreen(
                        state = vm.state.collectAsState().value,
                        onNavigateDetail = { id ->
                            navController.navigate(Routes.RECETA_DETAIL.replace("{id}", id))
                        }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(
            route = Routes.RECETA_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            AuthGuard(
                content = {
                    val vm: RecetaViewModel = hiltViewModel()
                    RecetaDetailScreen(
                        recetaId = id,
                        state = vm.state.collectAsState().value,
                        onLoad = vm::loadRecetaDetalle,
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
