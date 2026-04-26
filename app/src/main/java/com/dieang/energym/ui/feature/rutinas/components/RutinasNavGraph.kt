package com.dieang.energym.ui.feature.rutinas.components

import androidx.compose.runtime.Composable
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

@Composable
fun RutinasNavGraph(navController: NavController) {
    val vm: RutinaViewModel = hiltViewModel()
    RutinasScreen(
        state = vm.state.collectAsState().value,
        onNavigateDetail = { id ->
            navController.navigate(Routes.RUTINA_DETAIL.replace("{id}", id.toString()))
        }
    )
}
