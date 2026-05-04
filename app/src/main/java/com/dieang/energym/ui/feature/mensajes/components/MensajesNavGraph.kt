package com.dieang.energym.ui.feature.mensajes.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.mensajes.ChatScreen
import com.dieang.energym.ui.feature.mensajes.MensajeViewModel
import com.dieang.energym.ui.feature.mensajes.MensajesScreen
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.mensajesNavGraph(navController: NavController) {
    navigation(
        startDestination = "mensajes_main",
        route = Routes.MENSAJES
    ) {
        composable("mensajes_main") {
            AuthGuard(
                content = {
                    val vm: MensajeViewModel = hiltViewModel()
                    MensajesScreen(
                        state = vm.state.collectAsState().value,
                        onLoad = { vm.loadChats() },
                        onChatClick = { id ->
                            // En una implementación real, u1 es el usuario actual.
                            // Por ahora usamos un placeholder o simplificamos la ruta.
                            val route = Routes.CHAT.replace("{u1}", "me").replace("{u2}", id.toString())
                            navController.navigate(route)
                        }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(
            route = Routes.CHAT,
            arguments = listOf(
                navArgument("u1") { type = NavType.StringType },
                navArgument("u2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contactId = UUID.fromString(backStackEntry.arguments?.getString("u2") ?: "")
            AuthGuard(
                content = {
                    val vm: MensajeViewModel = hiltViewModel()
                    // El ID del usuario logueado lo manejamos dentro del VM o lo pasamos si es necesario.
                    // ChatScreen pide usuarioLogueadoId: UUID
                    ChatScreen(
                        contactoId = contactId,
                        usuarioLogueadoId = UUID.randomUUID(), // El VM debería proveer esto
                        state = vm.state.collectAsState().value,
                        onLoad = vm::loadConversacion,
                        onSend = vm::enviarMensaje,
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
