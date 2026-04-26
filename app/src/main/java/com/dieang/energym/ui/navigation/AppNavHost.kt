package com.dieang.energym.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dieang.energym.ui.feature.auth.components.authNavGraph
import com.dieang.energym.ui.feature.splash.components.splashNavGraph
import com.dieang.energym.ui.feature.home.components.HomeNavGraph
import com.dieang.energym.ui.feature.recetas.components.RecetasNavGraph
import com.dieang.energym.ui.feature.rutinas.components.RutinasNavGraph
import com.dieang.energym.ui.feature.sesiones.components.SesionesNavGraph
import com.dieang.energym.ui.feature.posts.components.PostsNavGraph
import com.dieang.energym.ui.feature.mensajes.components.MensajesNavGraph
import com.dieang.energym.ui.feature.usuario.components.UsuarioNavGraph

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

        // Home (requiere autenticación)
        composable(Routes.HOME) {
            AuthGuard(
                content = { HomeNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Recetas
        composable(Routes.RECETAS) {
            AuthGuard(
                content = { RecetasNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Rutinas
        composable(Routes.RUTINAS) {
            AuthGuard(
                content = { RutinasNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Sesiones
        composable(Routes.SESIONES) {
            AuthGuard(
                content = { SesionesNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Posts
        composable(Routes.POSTS) {
            AuthGuard(
                content = { PostsNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Mensajes
        composable(Routes.MENSAJES) {
            AuthGuard(
                content = { MensajesNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        // Perfil / Usuarios
        composable(Routes.USUARIOS) {
            AuthGuard(
                content = { UsuarioNavGraph(navController) },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
