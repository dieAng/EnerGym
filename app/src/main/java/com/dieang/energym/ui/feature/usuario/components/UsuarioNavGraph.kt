package com.dieang.energym.ui.feature.usuario.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.UUID
import com.dieang.energym.ui.feature.usuario.EditarPerfilScreen
import com.dieang.energym.ui.feature.usuario.PerfilScreen
import com.dieang.energym.ui.feature.usuario.SesionDetailScreen
import com.dieang.energym.ui.feature.usuario.SesionDetailViewModel
import com.dieang.energym.ui.feature.usuario.UsuarioViewModel
import com.dieang.energym.ui.navigation.Routes

@Composable
fun UsuarioNavGraph(navController: NavController) {
    val vm: UsuarioViewModel = hiltViewModel()
    PerfilScreen(
        state = vm.state.collectAsState().value,
        onNavigateSettings = {
            navController.navigate("editar_perfil")
        },
        onNavigateStats = {
            // Por ahora no hay pantalla de stats detalladas separada
        },
        onLogout = {
            vm.logout()
            navController.navigate(Routes.LOGIN) {
                popUpTo(0)
            }
        },
        onSesionClick = { id ->
            navController.navigate("sesion_detalle/$id")
        }
    )
}
