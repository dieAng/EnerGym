package com.dieang.energym.ui.feature.home

data class HomeState(
    val isLoading: Boolean = false,
    val nombreUsuario: String = "",
    val caloriasHoy: Int = 0,
    val rutinasPendientes: Int = 0,
    val recetasGuardadas: Int = 0,
    val error: String? = null
)
