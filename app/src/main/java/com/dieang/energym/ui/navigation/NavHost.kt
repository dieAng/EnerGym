package com.dieang.energym.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {

        // LOGIN
        composable(Route.Login.route) {
            AuthGuard(
                authViewModel = authViewModel,
                onAuthenticated = {
                    LaunchedEffect(Unit) {
                        navController.navigate(Route.Home.route) {
                            popUpTo(Route.Login.route) { inclusive = true }
                        }
                    }
                },
                onUnauthenticated = {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Route.Home.route) {
                                popUpTo(Route.Login.route) { inclusive = true }
                            }
                        }
                    )
                }
            )
        }

        // HOME (PROTEGIDO)
        composable(Route.Home.route) {
            AuthGuard(
                authViewModel = authViewModel,
                onAuthenticated = {
                    HomeScreen(
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(Route.Login.route) {
                                popUpTo(0)
                            }
                        },
                        goToPerfil = { navController.navigate(Route.Perfil.route) }
                    )
                },
                onUnauthenticated = {
                    LaunchedEffect(Unit) {
                        navController.navigate(Route.Login.route) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }

        // PERFIL (PROTEGIDO)
        composable(Route.Perfil.route) {
            AuthGuard(
                authViewModel = authViewModel,
                onAuthenticated = {
                    PerfilScreen(
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(Route.Login.route) {
                                popUpTo(0)
                            }
                        }
                    )
                },
                onUnauthenticated = {
                    LaunchedEffect(Unit) {
                        navController.navigate(Route.Login.route) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }
    }
}
