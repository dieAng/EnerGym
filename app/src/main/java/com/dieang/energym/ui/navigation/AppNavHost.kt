package com.dieang.energym.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dieang.energym.ui.feature.auth.components.authNavGraph
import com.dieang.energym.ui.feature.splash.components.splashNavGraph
import com.dieang.energym.ui.feature.home.components.homeNavGraph
import com.dieang.energym.ui.feature.recetas.components.recetasNavGraph
import com.dieang.energym.ui.feature.rutinas.components.rutinasNavGraph
import com.dieang.energym.ui.feature.sesiones.components.sesionesNavGraph
import com.dieang.energym.ui.feature.posts.components.postsNavGraph
import com.dieang.energym.ui.feature.mensajes.components.mensajesNavGraph
import com.dieang.energym.ui.feature.usuario.components.usuarioNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Splash (no requiere autenticación)
        splashNavGraph(navController)

        // Auth (login, registro, recuperación)
        authNavGraph(navController)

        // Home
        homeNavGraph(navController)

        // Recetas
        recetasNavGraph(navController)

        // Rutinas
        rutinasNavGraph(navController)

        // Sesiones
        sesionesNavGraph(navController)

        // Posts
        postsNavGraph(navController)

        // Mensajes
        mensajesNavGraph(navController)

        // Perfil / Usuarios
        usuarioNavGraph(navController)
    }
}
