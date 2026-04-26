package com.dieang.energym.ui.feature.mensajes.components

import androidx.compose.runtime.Composable
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

@Composable
fun MensajesNavGraph(navController: NavController) {
    val vm: MensajeViewModel = hiltViewModel()
    MensajesScreen(
        state = vm.state.collectAsState().value,
        onLoad = { vm.loadChats() },
        onChatClick = { id ->
            navController.navigate(Routes.CHAT.replace("{u2}", id.toString()))
        }
    )
}
