package com.dieang.energym.ui.feature.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.*
import com.dieang.energym.ui.feature.home.HomeScreen
import com.dieang.energym.ui.feature.home.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.dieang.energym.ui.navigation.Routes

@Composable
fun HomeNavGraph(navController: NavController) {
    val vm: HomeViewModel = hiltViewModel()
    HomeScreen(
        state = vm.state.collectAsState().value,
        onEvent = vm::onEvent,
        onNavigateRecetas = { navController.navigate(Routes.RECETAS) },
        onNavigateRutinas = { navController.navigate(Routes.RUTINAS) },
        onNavigateSesiones = { navController.navigate(Routes.SESIONES) }
    )
}

