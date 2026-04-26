package com.dieang.energym.ui.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.dieang.energym.ui.theme.EnerGymGreen
import com.dieang.energym.ui.theme.EnerGymTextSecondary

@Composable
fun EnerGymBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Solo mostrar si la ruta actual está en bottomNavItems
    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    if (showBottomBar) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(85.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .border(
                    1.dp,
                    EnerGymDivider,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

                NavigationBarItem(
                    icon = {
                        Box {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(28.dp)
                            )
                            if (item.hasNotification) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .align(Alignment.TopEnd)
                                        .offset(x = 2.dp, y = (-2).dp)
                                        .background(Color.White, CircleShape)
                                        .padding(1.5.dp)
                                        .background(EnerGymGreen, CircleShape)
                                )
                            }
                        }
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
                        unselectedIconColor = EnerGymTextSecondary.copy(alpha = 0.5f),
                        unselectedTextColor = EnerGymTextSecondary.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
