package com.dieang.energym.ui.feature.mensajes.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.mensajes.MensajeViewModel
import com.dieang.energym.ui.feature.mensajes.MensajesScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.mensajesNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.MENSAJES,
        route = Routes.MENSAJES
    ) {
        composable(Routes.MENSAJES) {
            val vm: MensajeViewModel = hiltViewModel()
            MensajesScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToChat = { id -> navController.navigate("${Routes.CHAT}/$id") }
            )
        }

        composable("${Routes.CHAT}/{id}") {
            val vm: MensajeViewModel = hiltViewModel()
            ChatScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
    }
}