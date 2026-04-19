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
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.mensajesNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.MENSAJES,
        route = "mensajes_graph"
    ) {
        composable(Routes.MENSAJES) {
            val vm: MensajeViewModel = hiltViewModel()
            MensajesScreen(
                state = vm.state.collectAsState().value,
                onLoad = { vm.loadChats() },
                onChatClick = { id ->
                    navController.navigate(Routes.CHAT.replace("{u2}", id.toString()))
                }
            )
        }

        composable(
            route = Routes.CHAT,
            arguments = listOf(
                navArgument("u1") { type = NavType.StringType; defaultValue = "" },
                navArgument("u2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val u2Id = backStackEntry.arguments?.getString("u2")?.let { UUID.fromString(it) }
                ?: UUID.randomUUID()
            val vm: MensajeViewModel = hiltViewModel()

            // Mock del ID del usuario logueado para la UI de burbujas
            val mockMyId = UUID.randomUUID() 

            ChatScreen(
                contactoId = u2Id,
                usuarioLogueadoId = mockMyId,
                state = vm.state.collectAsState().value,
                onLoad = { id -> vm.loadConversacion(id) },
                onSend = { id, text -> vm.enviarMensaje(id, text) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
