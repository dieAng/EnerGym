package com.dieang.energym.ui.feature.usuario.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.usuario.EditarPerfilScreen
import com.dieang.energym.ui.feature.usuario.PerfilScreen
import com.dieang.energym.ui.feature.usuario.UsuarioViewModel
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.usuarioNavGraph(navController: NavController) {
    navigation(
        startDestination = "perfil_main",
        route = Routes.USUARIOS
    ) {
        composable("perfil_main") {
            AuthGuard(
                content = {
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
                            navController.navigate(Routes.SESION_DETAIL.replace("{id}", id))
                        }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable("editar_perfil") {
            AuthGuard(
                content = {
                    val vm: UsuarioViewModel = hiltViewModel()
                    EditarPerfilScreen(
                        usuario = vm.state.collectAsState().value.usuario,
                        onSave = { nombre, objetivo ->
                            vm.updatePerfil(nombre, objetivo)
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
