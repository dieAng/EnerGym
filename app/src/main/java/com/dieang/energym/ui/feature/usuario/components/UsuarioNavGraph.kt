package com.dieang.energym.ui.feature.usuario.components

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

fun NavGraphBuilder.usuarioNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.USUARIOS,
        route = "usuario_graph"
    ) {
        composable(Routes.USUARIOS) {
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

        composable("sesion_detalle/{id}") {
            val vm: SesionDetailViewModel = hiltViewModel()
            SesionDetailScreen(
                state = vm.state.collectAsState().value,
                onBack = { navController.popBackStack() }
            )
        }

        composable("editar_perfil") {
            val vm: UsuarioViewModel = hiltViewModel()
            EditarPerfilScreen(
                usuario = vm.state.collectAsState().value.usuario,
                onSave = { nombre, objetivo ->
                    // vm.updatePerfil(nombre, objetivo)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.USUARIO_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val userIdString = backStackEntry.arguments?.getString("id")
            val userId = userIdString?.let { UUID.fromString(it) } ?: UUID.randomUUID()
            val vm: UsuarioViewModel = hiltViewModel()
            
            LaunchedEffect(userId) {
                vm.loadPerfil(userId)
            }
            
            PerfilScreen(
                state = vm.state.collectAsState().value,
                isPublicProfile = true,
                onNavigateSettings = { },
                onNavigateStats = { },
                onLogout = { },
                onSesionClick = { },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
