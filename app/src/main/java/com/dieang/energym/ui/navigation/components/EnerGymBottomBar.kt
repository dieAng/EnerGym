package com.dieang.energym.ui.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dieang.energym.ui.navigation.BottomNavItem
import com.dieang.energym.ui.navigation.bottomNavItems
import com.dieang.energym.ui.theme.EnerGymBlue
import com.dieang.energym.ui.theme.EnerGymDivider

@Composable
fun EnerGymBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Solo mostrar si la ruta actual está en bottomNavItems
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    if (showBottomBar) {
        NavigationBar(
            containerColor = Color(0xFF121212), // Dark para Figma
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(85.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = EnerGymBlue,
                        selectedTextColor = EnerGymBlue,
                        unselectedIconColor = Color.White.copy(alpha = 0.3f),
                        unselectedTextColor = Color.White.copy(alpha = 0.3f),
                        indicatorColor = Color.Transparent // Quitamos el círculo feo de Material 3
                    )
                )
            }
        }
    }
}
