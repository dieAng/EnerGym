package com.dieang.energym.ui.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Home : Route("home")
    object Perfil : Route("perfil")
    object Rutinas : Route("rutinas")
    object Recetas : Route("recetas")
}
