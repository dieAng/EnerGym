package com.dieang.energym.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(Routes.HOME, "Inicio", Icons.Default.Home)
    object Rutinas : BottomNavItem(Routes.RUTINAS, "Rutinas", Icons.Default.FitnessCenter)
    object Social : BottomNavItem(Routes.POSTS, "Comunidad", Icons.Default.Public)
    object Recetas : BottomNavItem(Routes.RECETAS, "Cocina", Icons.Default.RestaurantMenu)
    object Perfil : BottomNavItem(Routes.USUARIOS, "Perfil", Icons.Default.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Rutinas,
    BottomNavItem.Social,
    BottomNavItem.Recetas,
    BottomNavItem.Perfil
)
