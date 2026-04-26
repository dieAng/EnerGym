package com.dieang.energym.ui.feature.recetas.components

import androidx.compose.runtime.Composable
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

@Composable
fun RecetasNavGraph(navController: NavController) {
    val vm: RecetaViewModel = hiltViewModel()
    RecetasScreen(
        state = vm.state.collectAsState().value,
        onNavigateDetail = { id ->
            navController.navigate(Routes.RECETA_DETAIL.replace("{id}", id))
        }
    )
}
