package com.dieang.energym.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val hasNotification: Boolean = false,
) {
    object Home : BottomNavItem(Routes.HOME, "Inicio", Icons.Default.Home)
    object Rutinas : BottomNavItem(Routes.RUTINAS, "Rutinas", Icons.Default.FitnessCenter)
    object Social : BottomNavItem(Routes.POSTS, "Comunidad", Icons.Default.Public)
    object Mensajes : BottomNavItem(Routes.MENSAJES, "Mensajes", Icons.Default.Email, hasNotification = true)
    object Recetas : BottomNavItem(Routes.RECETAS, "Cocina", Icons.Default.RestaurantMenu)
    object Perfil : BottomNavItem(Routes.USUARIOS, "Perfil", Icons.Default.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Rutinas,
    BottomNavItem.Social,
    BottomNavItem.Mensajes,
    BottomNavItem.Recetas,
    BottomNavItem.Perfil,
)
