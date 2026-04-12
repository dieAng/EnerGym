package com.dieang.energym.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.mensajes.MensajeViewModel
import com.dieang.energym.ui.feature.posts.PostViewModel
import com.dieang.energym.ui.feature.recetas.RecetaViewModel
import com.dieang.energym.ui.feature.rutinas.RutinaViewModel
import com.dieang.energym.ui.feature.sesiones.SesionViewModel
import com.dieang.energym.ui.feature.usuario.UsuarioViewModel
import java.util.UUID

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {

    // HOME
    composable(Routes.HOME) {
        AuthGuard(
            content = {
                // Aquí tu pantalla Home
            },
            onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
        )
    }

    // USUARIOS
    composable(Routes.USUARIOS) {
        val vm: UsuarioViewModel = hiltViewModel()
        // UsuarioScreen(...)
    }

    // RECETAS
    composable(Routes.RECETAS) {
        val vm: RecetaViewModel = hiltViewModel()
        // RecetasScreen(...)
    }

    composable(Routes.RECETA_DETAIL) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")!!
        val vm: RecetaViewModel = hiltViewModel()

        vm.loadReceta(UUID.fromString(id))

        RecetaDetailScreen(
            state = vm.state.collectAsState().value
        )
    }


    // RUTINAS
    composable(Routes.RUTINAS) {
        val vm: RutinaViewModel = hiltViewModel()
        // RutinasScreen(...)
    }

    // SESIONES
    composable(Routes.SESIONES) {
        val vm: SesionViewModel = hiltViewModel()
        // SesionesScreen(...)
    }

    // POSTS
    composable(Routes.POSTS) {
        val vm: PostViewModel = hiltViewModel()
        // PostsScreen(...)
    }

    // MENSAJES
    composable(Routes.MENSAJES) {
        val vm: MensajeViewModel = hiltViewModel()
        // MensajesScreen(...)
    }